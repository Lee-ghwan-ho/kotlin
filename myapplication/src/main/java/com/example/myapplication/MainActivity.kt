package com.example.myapplication

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityServiceBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            binding.btnbirth.text = "${year}/${month}/${day}"
        }
        val radio = binding.btnstudent
        var radio2 = binding.btnprofessor
        var radio3 = binding.btnworker
        binding.btnbirth.setOnClickListener{
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                binding.birth.text = "${year}/${month}/${day}"
        }
            DatePickerDialog(this, data, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
//https://krapoi.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9CKotlin-DatePickerDialog-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0

            }
        binding.btnjoin.setOnClickListener{
            if (binding.editTextTextEmailAddress.text.isNullOrEmpty()||binding.editTextTextPassword.text.isNullOrEmpty()||binding.editTextTextPassword.text.isNullOrEmpty()||
                binding.editTextTextPassword2.text.isNullOrEmpty()||binding.editTextName.text.isNullOrEmpty()||binding.editTextName.text.isNullOrEmpty()||binding.editTextPhone.text.isNullOrEmpty()){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("공백 필드")
                    .setMessage("모든 필드를 채워주세요 ")
                    .setPositiveButton("확인"){dialog,_ -> dialog.dismiss()}
                    .show()
        }
            //https://velog.io/@jinny_0422/Android-EditText%EC%9D%98-%EC%9D%B4%EB%A9%94%EC%9D%BC-%ED%98%95%EC%8B%9D-%EA%B2%80%EC%82%AC%ED%95%98%EA%B8%B0
            if(!binding.editTextTextEmailAddress.text.contains("@")){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("E-mail")
                    .setMessage("잘못된 형식의 E-mail입니다")
                    .setPositiveButton("확인"){dialog,_ -> dialog.dismiss()}
                    .show()
            }
            if(binding.editTextTextPassword.text.toString() !=binding.editTextTextPassword2.text.toString()){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("비밀번호")
                    .setMessage("비밀번호는 같아야 합니다 ")
                    .setPositiveButton("확인"){dialog,_ -> dialog.dismiss()}
                    .show()
            }
            if(binding.editTextTextPassword.text.length < 8){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("비밀번호")
                    .setMessage("비밀번호는 8자리 이상이여야합니다 ")
                    .setPositiveButton("확인"){dialog,_ -> dialog.dismiss()}
                    .show()
            }
            if(binding.btnstudent.isChecked){
                val radio = binding.btnstudent.text
            }
            if(binding.btnprofessor.isChecked){
                var radio = binding.btnstudent.text
            }
            if(binding.btnworker.isChecked){
                var radio = binding.btnstudent.text
            }






            val builder = AlertDialog.Builder(this)
            builder.setTitle("회원가입")
                .setMessage("다음정보로 가입을 진행하시겠습니까?\n E-Mail: ${binding.editTextTextEmailAddress.text.toString()}\n" +
                        "이름:${binding.editTextName.text.toString()} \n 휴대폰 번호:${binding.editTextPhone.text.toString()}\n 생년 월일:${binding.birth.text.toString()}\n " )

                .setPositiveButton("예"){dialog,_ -> dialog.dismiss()}
                .setNegativeButton("아니오"){dialog,_ -> dialog.dismiss()}
                .show()

            //birth.text.toString() , editTextPhone.text.toString()
    }
    }
}