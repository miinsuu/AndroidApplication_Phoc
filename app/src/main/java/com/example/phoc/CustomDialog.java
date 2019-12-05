package com.example.phoc;

import android.app.Dialog;
import android.content.Context;
import android.media.ExifInterface;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static android.media.ExifInterface.TAG_EXPOSURE_TIME;
import static android.media.ExifInterface.TAG_FLASH;
import static android.media.ExifInterface.TAG_ISO_SPEED_RATINGS;
import static android.media.ExifInterface.TAG_WHITE_BALANCE;

import static com.example.phoc.MainActivity.exposure;
import static com.example.phoc.MainActivity.flash_count;
import static com.example.phoc.MainActivity.iso;

public class CustomDialog {
    private Context context;

    public CustomDialog(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(final ExifInterface exif) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
        final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        final EditText message = (EditText) dlg.findViewById(R.id.mesgase);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // '확인' 버튼 클릭시 메인 액티비티에서 설정한 main_label에
                // 커스텀 다이얼로그에서 입력한 메시지를 대입한다.
                String name = message.getText().toString();

                saveExif(name);

                Toast.makeText(context, "설정이 저장되었습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "취소하셨습니다.", Toast.LENGTH_SHORT).show();

                // 커스텀 다이얼로그를 종료한다.
                dlg.dismiss();
            }
        });
    }

    private void saveExif(String name) {
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Pho_C/");
        if (!folder.exists()) {
            folder.mkdirs(); }

        File files = new File(folder+"/cameraExif.json");
        //cameraExif.json 파일 유무 확인
        if(files.exists()==true) { //파일이 있을시
            JSONParser parser = new JSONParser();

            Object obj = null;
            try {
                obj = parser.parse(new FileReader(folder+"/cameraExif.json"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            JSONObject cameraExifObject = (JSONObject) obj;

            JSONArray exifArray = (JSONArray) cameraExifObject.get("EXIF");

            //Exif 정보가 들어갈 JSONObject 선언
            JSONObject exifValue = new JSONObject();
            //정보 입력
            exifValue.put("NAME", name);
            exifValue.put("TAG_FLASH", String.valueOf(flash_count));
            //exifValue.put("TAG_WHITE_BALANCE", exif.getAttribute(TAG_WHITE_BALANCE));
            exifValue.put("TAG_ISO_SPEED_RATINGS", String.valueOf(iso));
            exifValue.put("TAG_EXPOSURE_TIME", String.valueOf(exposure));

            //Array에 입력
            exifArray.add(exifValue);
            //최종 Object에 EXIF 값들 저장
            cameraExifObject.put("EXIF", exifArray);


            File file = new File(folder,"cameraExif.json");

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(cameraExifObject.toString());
                bw.close();
                //Toast.makeText(this, "새로운 EXIF정보가 "+ folder + "/cameraExif.json 에 추가되었습니다.", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else { //파일이 없을시
            //최종 완성될 JSONObject 선언(전체)
            JSONObject cameraExifObject = new JSONObject();
            //여러 사용자의 Exif정보를 담을 Array 선언
            JSONArray exifArray = new JSONArray();
            //Exif 정보가 들어갈 JSONObject 선언
            JSONObject exifValue = new JSONObject();
            //정보 입력
            exifValue.put("NAME", name);
            exifValue.put("TAG_FLASH", String.valueOf(flash_count));
            //exifValue.put("TAG_WHITE_BALANCE", exif.getAttribute(TAG_WHITE_BALANCE));
            exifValue.put("TAG_ISO_SPEED_RATINGS", String.valueOf(iso));
            exifValue.put("TAG_EXPOSURE_TIME", String.valueOf(exposure));

            //Array에 입력
            exifArray.add(exifValue);
            //최종 Object에 EXIF 값들 저장
            cameraExifObject.put("EXIF", exifArray);


            File file = new File(folder,"cameraExif.json");

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(cameraExifObject.toString());
                bw.close();
                //Toast.makeText(this, "EXIF정보가 "+ folder + "/cameraExif.json 에 저장되었습니다.", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}