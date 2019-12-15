package com.example.phoc;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ExifInterface;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Range;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static android.media.ExifInterface.TAG_EXPOSURE_TIME;
import static android.media.ExifInterface.TAG_FLASH;
import static android.media.ExifInterface.TAG_ISO_SPEED_RATINGS;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE_2 = 300;
    private TextureView mTextureView;
    private CameraDevice cameraDevice;
    private CaptureRequest.Builder mPreviewBuiler;
    private CameraCaptureSession mPreviewSession;
    private CameraManager manager;
    // 카메라 설정에 관한 변수
    private Size mPreviewSize;
    private StreamConfigurationMap map;
    private Uri selectedImageUri;
    private String sendImageUri;
    private ExifInterface exif;
    static int flash_count;
    static long exposure;
    static int iso;
    static int whiteBalance;
    private SeekBar isoSeek;
    private SeekBar exposureSeek;
    private SeekBar wbSeek;
    private boolean haveExif;
    Intent intent;
    private String titleName;
    private static final String TAG = "MainActivity";
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray(4);
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // permission check
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            // 권한이 없으면 권한을 요청한다.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    200);
        }else {
            // 권한이 있을 경우에만 layout을 전개한다.
            intent = getIntent(); /*데이터 수신*/
            titleName =  intent.getStringExtra("titleName");
            if((intent.getStringExtra("Exif")) != null && (intent.getStringExtra("Exif")).equals("Exif"))
                haveExif = true;
            initLayout();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==200 && grantResults.length>0){
            boolean permissionGranted=true;
            for(int result : grantResults){
                if(result != PackageManager.PERMISSION_GRANTED){
                    // 사용자가 권한을 거절했다.
                    permissionGranted=false;
                    break;
                }
            }

            if(permissionGranted){
                // 권한 요청을 수락한 경우에 layout을 전개한다.
                initLayout();
            }else{
                Toast.makeText(this,
                        "권한을 수락해야 어플 이용이 가능합니다",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    private void initLayout() {
        setContentView(R.layout.activity_camera);
        mTextureView=findViewById(R.id.preview);
        mTextureView.setSurfaceTextureListener(mSurfaceTextureListener);

        ((Button)findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    takePicture();

                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        ((Button)findViewById(R.id.list)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Pho_C/");
                if (!folder.exists()) {
                    folder.mkdirs(); }

                File files = new File(folder+"/cameraExif.json");
                //cameraExif.json 파일 유무 확인
                if(files.exists()==true) { //파일이 있을시
                    JSONParser parser = new JSONParser();

                    Object obj = null;
                    try {
                        obj = parser.parse(new FileReader(folder + "/cameraExif.json"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    JSONObject cameraExifObject = (JSONObject) obj;

                    JSONArray exifArray = (JSONArray) cameraExifObject.get("EXIF");

                    final String[] oItems = new String[exifArray.size()];

                    for(int i = 0; i < exifArray.size(); i++) {
                        JSONObject tempObect = (JSONObject) exifArray.get(i);

                        oItems[i] = (String)tempObect.get("NAME");
                    }

                    AlertDialog.Builder oDialog = new AlertDialog.Builder(MainActivity.this);

                    oDialog.setTitle("● 원하는 프리셋을 선택하세요 ●")
                            .setItems(oItems, new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
                                    CustomDialogConfirm customDialog = new CustomDialogConfirm(MainActivity.this);
                                    // 새로운 프리셋을 적용하면 발생할 이벤트의 리스너
                                    customDialog.setOnUpdateEvent(new CustomDialogConfirm.UpdateListener() {
                                        @Override
                                        public void onUpdateEvent() {
                                            //새로운 프리셋으로 카메라 업데이트
                                            updatePreview();
                                        }
                                    });
                                    // 커스텀 다이얼로그를 호출한다.
                                    customDialog.callFunction(oItems[which]);
                                }
                            })
                            .create().show();

                } //if문 종료
            }

        });

        final TextView main_label = (TextView) findViewById(R.id.main_label);

        ((Button)findViewById(R.id.save)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 커스텀 다이얼로그를 생성한다. 사용자가 만든 클래스이다.
                CustomDialog customDialog = new CustomDialog(MainActivity.this);

                // 커스텀 다이얼로그를 호출한다.
                customDialog.callFunction();
            }
        });

        if(haveExif == true) {
            //exifbutton 클릭 시 사진의 uri를 가진 intent 도착

            String imageUriString = intent.getStringExtra("imageUriString");
            selectedImageUri = Uri.parse(imageUriString);

            //사진에서 exif값 추출
            try {
                exif = new ExifInterface(getPath(selectedImageUri));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Exif Error!", Toast.LENGTH_LONG).show();
            }

            //사진옆 버튼 선택시, 해당사진의 설정값으로 설정되어 카메라실행
            //플래시 설정
            if (exif.getAttribute(TAG_FLASH).equals("1"))
                flash_count = 1;
            else if (exif.getAttribute(TAG_FLASH).equals("0"))
                flash_count = 0;
            //ISO 설정
            if (!exif.getAttribute(TAG_ISO_SPEED_RATINGS).isEmpty())
                iso = Integer.parseInt(exif.getAttribute(TAG_ISO_SPEED_RATINGS));
            //exposure time(셔터스피드) 설정
            if (!exif.getAttribute(TAG_EXPOSURE_TIME).isEmpty()) {
                double temp = exif.getAttributeDouble(TAG_EXPOSURE_TIME, 0) * 1000000000l;
                exposure = (long) temp;
            }

        }
         // 플래시 버튼 이벤트
        ((Button)findViewById(R.id.flash_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //나머지 창 없애기
                isoSeek.setVisibility(View.GONE);
                ((TextView)findViewById(R.id.isoInfoText)).setVisibility(View.GONE);
                exposureSeek.setVisibility(View.GONE);
                ((TextView)findViewById(R.id.exposureInfoText)).setVisibility(View.GONE);

                flash_count++;

                if(flash_count > 1)
                    flash_count = 0;

                if(flash_count == 1) {
                    ((Button) findViewById(R.id.flash_btn)).setText("플래시ON");
                    updatePreview();
                }
                else {
                    ((Button)findViewById(R.id.flash_btn)).setText("플래시OFF");
                    updatePreview();
                }

            }
        });

        //ISO seekbar로 설정
        isoSeek = (SeekBar)findViewById(R.id.isoSeek);
        isoSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                iso = progress;
                ((TextView)findViewById(R.id.isoInfoText)).setText(String.valueOf(iso));
                updatePreview();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //ISO버튼 seekbar 띄우기
        ((Button)findViewById(R.id.ISO_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //나머지 창 없애기
                exposureSeek.setVisibility(View.GONE);
                ((TextView)findViewById(R.id.exposureInfoText)).setVisibility(View.GONE);

                if(!isoSeek.isShown() && (((TextView)findViewById(R.id.isoInfoText)).getVisibility() == View.GONE)) {
                    isoSeek.setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.isoInfoText)).setVisibility(View.VISIBLE);
                }
                else if(isoSeek.isShown() && ((((TextView)findViewById(R.id.isoInfoText)).getVisibility() == View.VISIBLE))) {
                    isoSeek.setVisibility(View.GONE);
                    ((TextView)findViewById(R.id.isoInfoText)).setVisibility(View.GONE);
                }
            }
        });

        //exposure seekbar로 설정
        exposureSeek = (SeekBar)findViewById(R.id.exposureSeek);
        exposureSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                exposure = 1000000000l /(long)progress;
                ((TextView)findViewById(R.id.exposureInfoText)).setText("1/"+progress+" s");
                updatePreview();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //셔터스피드버튼 seekbar 띄우기
        ((Button)findViewById(R.id.exposure_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //나머지 창 없애기
                isoSeek.setVisibility(View.GONE);
                ((TextView)findViewById(R.id.isoInfoText)).setVisibility(View.GONE);

                if(!exposureSeek.isShown() && (((TextView)findViewById(R.id.exposureInfoText)).getVisibility() == View.GONE)) {
                    exposureSeek.setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.exposureInfoText)).setVisibility(View.VISIBLE);
                }
                else if(exposureSeek.isShown() && (((TextView)findViewById(R.id.exposureInfoText)).getVisibility() == View.VISIBLE)) {
                    exposureSeek.setVisibility(View.GONE);
                    ((TextView)findViewById(R.id.exposureInfoText)).setVisibility(View.GONE);
                }
            }
        });


        //white balance seekbar로 설정
//        wbSeek = (SeekBar)findViewById(R.id.wbSeek);
//        wbSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//                whiteBalance = progress;
//                ((TextView)findViewById(R.id.wbInfoText)).setText(String.valueOf(progress));
//                //updatePreview();
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//        //WB버튼 seekbar 띄우기
//        ((Button)findViewById(R.id.WB_btn)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //나머지 창 없애기
//                isoSeek.setVisibility(View.GONE);
//                ((TextView)findViewById(R.id.isoInfoText)).setVisibility(View.GONE);
//                exposureSeek.setVisibility(View.GONE);
//                ((TextView)findViewById(R.id.exposureInfoText)).setVisibility(View.GONE);
//
//                if(!wbSeek.isShown() && (((TextView)findViewById(R.id.wbInfoText)).getVisibility() == View.GONE)) {
//                    wbSeek.setVisibility(View.VISIBLE);
//                    ((TextView)findViewById(R.id.wbInfoText)).setVisibility(View.VISIBLE);
//                }
//                else if(wbSeek.isShown() && (((TextView)findViewById(R.id.wbInfoText)).getVisibility() == View.VISIBLE)) {
//                    wbSeek.setVisibility(View.GONE);
//                    ((TextView)findViewById(R.id.wbInfoText)).setVisibility(View.GONE);
//                }
//            }
//        });


    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }



    private void takePicture() throws CameraAccessException {
        Size[] jpegSizes = null;
        if (map != null) jpegSizes = map.getOutputSizes(ImageFormat.JPEG);
        int width = 640;
        int height = 480;
        if (jpegSizes != null && 0 < jpegSizes.length) {
            width = jpegSizes[0].getWidth();
            height = jpegSizes[0].getHeight();
        }
        final ImageReader imageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);

        List<Surface> outputSurfaces = new ArrayList<Surface>(2);
        outputSurfaces.add(imageReader.getSurface());
        outputSurfaces.add(new Surface(mTextureView.getSurfaceTexture()));

        // ImageCapture를 위한 CaputureRequest.Builder 객체
        final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
        captureBuilder.addTarget(imageReader.getSurface());

        // 이전 카메라 api는 이 기능 지원X
        // 이미지를 캡처하는 순간에 제대로 사진 이미지가 나타나도록 3A를 자동으로 설정
        //captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        captureBuilder.set(CaptureRequest.CONTROL_AWB_LOCK, true);
        captureBuilder.set(CaptureRequest.CONTROL_AWB_MODE, CameraMetadata.CONTROL_AWB_MODE_AUTO);
        captureBuilder.set(CaptureRequest.CONTROL_AE_LOCK, true);
        captureBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_OFF);
        //플래시 설정
        if(flash_count == 1)
            captureBuilder.set(CaptureRequest.FLASH_MODE,CameraMetadata.FLASH_MODE_SINGLE);
        else if(flash_count == 0)
            captureBuilder.set(CaptureRequest.FLASH_MODE,CameraMetadata.FLASH_MODE_OFF);
        //ISO 설정
        captureBuilder.set(CaptureRequest.SENSOR_SENSITIVITY, iso);
        //셔터스피드(노출시간, exposure time) 설정
        captureBuilder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, exposure);
        //white balance 설정
//        captureBuilder.set(CaptureRequest.COLOR_CORRECTION_MODE, CaptureRequest.COLOR_CORRECTION_MODE_TRANSFORM_MATRIX);
//        captureBuilder.set(CaptureRequest.COLOR_CORRECTION_MODE, CaptureRequest.COLOR_CORRECTION_MODE_TRANSFORM_MATRIX);
//        captureBuilder.set(CaptureRequest.COLOR_CORRECTION_GAINS, computeTemperature(whiteBalance));






        int rotationn = getWindowManager().getDefaultDisplay().getRotation();
        captureBuilder.set(CaptureRequest.JPEG_ORIENTATION,ORIENTATIONS.get(rotationn));

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        final String imageFileName = "JPEG_" + timeStamp + ".jpg";
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Pho_C/");
        if (!folder.exists()) {
            folder.mkdirs(); }

        final File file = new File(folder,imageFileName);

//        if(haveExif == false)
//        {
//            //전시화면에 보낼 저장된 사진URI
////            sendImageUri = Uri.fromFile(file).toString();
////            Log.e("file->Uri->String = sendImageUri", sendImageUri);
//        }

        // 이미지를 캡처할 때 자동으로 호출된다.
        ImageReader.OnImageAvailableListener readerListener =
                new ImageReader.OnImageAvailableListener() {
                    @Override
                    public void onImageAvailable(ImageReader reader) {
                        Image image = null;
                        try {
                            image = imageReader.acquireLatestImage();
                            ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                            byte[] bytes = new byte[buffer.capacity()];
                            buffer.get(bytes);
                            save(bytes);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    private void save(byte[] bytes) throws IOException {

                        OutputStream output = null;
                        try {
                            output = new FileOutputStream(file);
                            output.write(bytes);
                        }
                        finally {
                            if(output!=null) output.close();
                        }
                    }
                };
        // 이미지를 캡처하는 작업은 메인 스레드가 아닌 스레드 핸들러로 수행한다.
        HandlerThread thread = new HandlerThread("CameraPicture");
        thread.start();
        final Handler backgroundHandler = new Handler(thread.getLooper());

        // ImageReader와 ImageReader.OnImageAvailableListener객체를 서로 연결시켜주기 위해 설정
        imageReader.setOnImageAvailableListener(readerListener, backgroundHandler);

        // 사진 이미지를 캡처한 이후 호출되는 메소드
        final CameraCaptureSession.CaptureCallback captureCallback =
                new CameraCaptureSession.CaptureCallback() {
                    @Override
                    public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                                   @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                        super.onCaptureCompleted(session, request, result);
                        Toast.makeText(MainActivity.this, "saved:"+file, Toast.LENGTH_LONG).show();
                        //사진 저장후 외부저장소 스캐닝
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://"+ Environment.getExternalStorageDirectory()+"/DCIM/Pho_C/"+imageFileName)));
                        // 이미지가 성공적으로 캡처되면 다시 미리보기를 수행한다.
                        startPreview();
                    }
                };
            /*
            사진 이미지를 캡처하는데 사용하는 CameraCaptureSession을 생성한다.
            이미 존재하면 기존 세션은 자동으로 종료
            */
        try {
            cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    try {
                        session.capture(captureBuilder.build(), captureCallback, backgroundHandler);

                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                }
            }, backgroundHandler);
        } catch (CameraAccessException ex) {
            Log.e(TAG, "takePicture() createCaptureRequest fail");
            ex.printStackTrace();
        }

        //플래시 초기화
        flash_count = 0;
        //갤러리앱으로 이동
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE_2);
//            Intent intent = new Intent(MainActivity.this, Upload.class);
//            Log.e("인텐트 보내기전 sendImageUri", sendImageUri);
//            intent.putExtra("imageUriString", sendImageUri); /*송신*/
//            startActivity(intent);
            //updatePreview();
            //finish();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE_2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData(); //갤러리에서 선택한 사진 URI 획득
            //선택사진URI 가지고 전시화면으로 이동
            Intent intent = new Intent(MainActivity.this, Upload.class);
            String UriToString = selectedImageUri.toString();
            intent.putExtra("imageUriString", UriToString); /*송신*/
            intent.putExtra("titleName", titleName); /*송신*/
            startActivity(intent);
        }
    }

    // textureView가 화면에 정상적으로 출력되면 onSurfaceTextureAvailable()호출
    private TextureView.SurfaceTextureListener mSurfaceTextureListener=
            new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                    // cameraManager생성하는 메소드
                    openCamera(width, height);
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    return false;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                }
            }; //TextureView.SurfaceTextureListener


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openCamera(int width, int height) {
        // CameraManager 객체 생성
        manager= (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            // default 카메라를 선택한다.
            String cameraId = manager.getCameraIdList()[0];

            // 카메라 특성 알아보기
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            int level = characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
            Range<Integer> fps[]=characteristics.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_TARGET_FPS_RANGES);
            Log.d(TAG,"maximum frame rate is :"+fps[fps.length-1]+"hardware level = "+level);

            // StreamConfigurationMap 객체에는 카메라의 각종 지원 정보가 담겨있다.
            map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

            // 미리보기용 textureview 화면크기용을 설정 <- 제공할 수 있는 최대크기를 가리킨다.
            mPreviewSize=map.getOutputSizes(SurfaceTexture.class)[0];
            Range<Integer> fpsForVideo[] = map.getHighSpeedVideoFpsRanges();
            Log.e(TAG, "for video :"+fpsForVideo[fpsForVideo.length-1]+" preview Size width:"+mPreviewSize.getWidth()+", height"+height);

            // 권한에 대한
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                // 권한이 없으면 권한을 요청한다.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        200);
            }else {
                // CameraDevice생성
                manager.openCamera(cameraId, mStateCallback, null);
            }

        } catch (CameraAccessException e) {
            Log.e(TAG, "openCamera() :카메라 디바이스에 정상적인 접근이 안됩니다.");
        }
    }


    private CameraDevice.StateCallback mStateCallback
            = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            // CameraDevice 객체 생성
            cameraDevice = camera;
            // CaptureRequest.Builder객체와 CaptureSession 객체 생성하여 미리보기 화면을 실행시킨다.
            startPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {

        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {

        }
    };

    private void startPreview() {
        if( cameraDevice==null ||
                !mTextureView.isAvailable() ||
                mPreviewSize==null){
            Log.e(TAG, "startPreview() fail , return ");
            return;
        }

        SurfaceTexture texture = mTextureView.getSurfaceTexture();
        Surface surface = new Surface(texture);
        try {
            mPreviewBuiler = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        } catch (CameraAccessException e) {
            Log.e(TAG, "CaptureRequest 객체 생성 실패");
            e.printStackTrace();
        }
        mPreviewBuiler.addTarget(surface);


        try {
            cameraDevice.createCaptureSession(Arrays.asList(surface),  // / 미리보기용으로 위에서 생성한 surface객체 사용
                    new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession session) {
                            mPreviewSession=session;
                            updatePreview();
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                        }
                    }, null );
        } catch (CameraAccessException e) {
            Log.e(TAG, "CaptureSession 객체 생성 실패");
            e.printStackTrace();
        }
    }

    private void updatePreview() {
        if(cameraDevice==null){
            return;
        }
        //mPreviewBuiler.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
//        if(flash_count == 1)
//            mPreviewBuiler.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_SINGLE);
//        else if(flash_count == 0)
//            mPreviewBuiler.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
        //mPreviewBuiler.set(CaptureRequest.COLOR_CORRECTION_MODE, CameraMetadata.SHU);
        //mPreviewBuiler.set(CaptureResult.FLASH_STATE, CameraMetadata.FLASH_STATE_CHARGING);
        mPreviewBuiler.set(CaptureRequest.CONTROL_AWB_LOCK, true);
        mPreviewBuiler.set(CaptureRequest.CONTROL_AWB_MODE, CameraMetadata.CONTROL_AWB_MODE_AUTO);
        mPreviewBuiler.set(CaptureRequest.CONTROL_AE_LOCK, true);
        mPreviewBuiler.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_OFF);
        //ISO 설정
        mPreviewBuiler.set(CaptureRequest.SENSOR_SENSITIVITY, iso);
        //셔터스피드(노출시간, exposure time) 설정
        mPreviewBuiler.set(CaptureRequest.SENSOR_EXPOSURE_TIME, exposure);
        //white balance 설정
        //mPreviewBuiler.set(CaptureRequest.COLOR_CORRECTION_MODE, CaptureRequest.COLOR_CORRECTION_MODE_TRANSFORM_MATRIX);
        //mPreviewBuiler.set(CaptureRequest.COLOR_CORRECTION_GAINS, computeTemperature(whiteBalance));

        HandlerThread thread = new HandlerThread("CameraPreview");
        thread.start();
        Handler backgroundHandler = new Handler(thread.getLooper());
        try {
            mPreviewSession.setRepeatingRequest(mPreviewBuiler.build(), null, backgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    //white balance 설정을 위한 RGB를 이용해 vector만드는 함수
//    private static RggbChannelVector computeTemperature(final int factor)
//    {
//        return new RggbChannelVector(0.635f + (0.0208333f * factor), 1.0f, 1.0f, 3.7420394f + (-0.0287829f * factor));
//    }


}
