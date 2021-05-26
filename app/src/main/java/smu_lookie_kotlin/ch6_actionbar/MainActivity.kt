package smu_lookie_kotlin.ch6_actionbar

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    var check : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.main_menu_login->{
                var builder = AlertDialog.Builder(this)
                builder.setTitle("회원 추가")
                var view = layoutInflater.inflate(R.layout.login_dialog_view,null)
                builder.setView(view)

                var positiveListener = object: DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int){
                        var alert = dialog as AlertDialog
                        val id  = alert.findViewById<EditText>(R.id.login_dialog_view_id)?.text.toString()
                        val pw = alert.findViewById<EditText>(R.id.login_dialog_view_pw)?.text.toString()

                        Asynctask().execute(getString(R.string.server_url),id,pw)
                    }
                }
                builder.setPositiveButton("로그인", positiveListener)
                builder.setNegativeButton("취소",null)
                builder.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class Asynctask : AsyncTask<String?, Void, String?>(){
        var id : String? = null
        override fun onPreExecute() {

        }
        override fun doInBackground(vararg params: String?): String? {
            var client = OkHttpClient()
            id = params[1]
            try{
                var okHttpApi = OkHttpApi()
                var response = okHttpApi.POST(client,params[0],okHttpApi.JsonBody(params[1],params[2]))
                return response
            } catch (e : IOException){

            }
            return null
        }

        override fun onPostExecute(result: String?) {
            if(!result.isNullOrEmpty()){
                //Toast.makeText(applicationContext,result,Toast.LENGTH_SHORT).show()
                var JSONObject : JSONObject = JSONObject(result)
                check= JSONObject.getInt("result")
                if(check.equals(1))
                    Toast.makeText(applicationContext,"환영합니다 ${id}님",Toast.LENGTH_LONG).show()
            }
        }
    }
}
