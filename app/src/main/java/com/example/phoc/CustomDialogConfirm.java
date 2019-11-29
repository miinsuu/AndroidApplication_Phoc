package com.example.phoc;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.example.camera2.MainActivity.exposure;
import static com.example.camera2.MainActivity.flash_count;
import static com.example.camera2.MainActivity.iso;

public class CustomDialogConfirm {

    private Context context;

    public CustomDialogConfirm(Context context) {
        this.context = context;
    }

    // 호출할 다이얼로그 함수를 정의한다.
    public void callFunction(final String name) {

        // 커스텀 다이얼로그를 정의하기위해 Dialog클래스를 생성한다.
         final Dialog dlg = new Dialog(context);

        // 액티비티의 타이틀바를 숨긴다.
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 커스텀 다이얼로그의 레이아웃을 설정한다.
        dlg.setContentView(R.layout.custom_dialog_2);

        // 커스텀 다이얼로그를 노출한다.
        dlg.show();

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        TextView title = (TextView) dlg.findViewById(R.id.title);
        title.setText(name);
        final Button okButton = (Button) dlg.findViewById(R.id.okButton);
        final Button cancelButton = (Button) dlg.findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                // '확인' 버튼 클릭시
                Toast.makeText(context, "\""+name+"\" 카메라설정을 불러 옵니다.", Toast.LENGTH_SHORT).show();

                //카메라에 설정값을 설정한 뒤 다시 카메라액티비티 띄우기 구현

                //JSON 파일에서 선택한 프리셋 값 읽어오기
                File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Pho_C/");

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

                for(int i =0; i < exifArray.size(); i++)
                {
                    JSONObject iJson = (JSONObject)exifArray.get(i);

                    if(((String)iJson.get("NAME")).equals(name)) //선택한 프리셋 이름과 같은 정보를 찾는다.
                    {
                        //exposure time 추출
                        double temp = Double.parseDouble((String)iJson.get("TAG_EXPOSURE_TIME")) * 1000000000l;
                        exposure = (long)temp;
                        //ISO 추출
                        iso = Integer.parseInt((String)iJson.get("TAG_ISO_SPEED_RATINGS"));
                        //플래시 여부 추출
                        if(((String)iJson.get("TAG_FLASH")).equals("1"))
                            flash_count = 1;
                        else if(((String)iJson.get("TAG_FLASH")).equals("0"))
                            flash_count = 0;
                        //새로운 프리셋 값으로 카메라 업데이트
                        mUpdateListener.onUpdateEvent();
                        break;
                    }
                }

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


    private UpdateListener mUpdateListener;
    //새로운 프리셋으로 카메라를 업데이트 하기위한 인터페이스
    public interface UpdateListener{
        void onUpdateEvent(); //업데이트가 필요할때 호출할 함수
    }
    //업데이트리스너를 달아줄 함수
    public void setOnUpdateEvent(UpdateListener listener){
        mUpdateListener = listener;
    }




}
