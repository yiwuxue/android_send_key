package com.qefee.testinstrumentation;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	TextView tv_showkey;
	Button btn_submit;
	EditText et_keycode;
	Instrumentation instrumentation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		instrumentation = new Instrumentation();
		getViews();

		// 通过按钮点击,模拟事件
		btn_submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				et_keycode.clearFocus();
				btn_submit.requestFocus();
				String keycodeStr = et_keycode.getText().toString();

				if (keycodeStr == null || "".equals(keycodeStr)) {
					Toast.makeText(MainActivity.this, "please input a keycode",
							Toast.LENGTH_SHORT).show();
					return;
				}

				final int keycode = Integer.parseInt(keycodeStr);

				// 必需在线程中运行,否者报错
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						instrumentation.sendKeyDownUpSync(keycode);
					}
				});
				t.start();

			}
		});
	}

	/**
	 * 
	 */
	private void getViews() {
		tv_showkey = (TextView) findViewById(R.id.tv_showcode);
		btn_submit = (Button) findViewById(R.id.btn_submit);
		et_keycode = (EditText) findViewById(R.id.et_keycode);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean onKeyDown = super.onKeyDown(keyCode, event);
		tv_showkey.setText("des = " + KeyEvent.keyCodeToString(keyCode)
				+ " : code = " + keyCode);
		System.out.println(event);
		// System.out.println(event.getAction());
		// System.out.println(event.getCharacters());
		// System.out.println(event.getDeviceId());
		// System.out.println(event.getDisplayLabel());
		// System.out.println(event.getDownTime());
		// System.out.println(event.getEventTime());
		// System.out.println(event.getFlags());
		// System.out.println(event.getKeyCode());
		//
		// System.out.println(KeyEvent.keyCodeToString(keyCode));

		return onKeyDown;
	}
}
