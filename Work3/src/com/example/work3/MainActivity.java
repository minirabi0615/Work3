package com.example.work3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
	 
	// Button�̔z��
	Button mButton[];

	// Id�̔z��
	int mId[] = { R.id.button0, R.id.button1, R.id.button2, R.id.button3,
			R.id.button4, R.id.button5, R.id.button6, R.id.button7,
			R.id.button8, R.id.button9, R.id.buttonPlus, R.id.buttonMinus,
			R.id.buttonEqual, R.id.buttonTen, R.id.buttonClear };
	
	// �L�[
	private final int KEY_PLUS = 10;
	private final int KEY_MINUS = 11;
	private final int KEY_EQUAL = 12;
	private final int KEY_TEN = 13;
	private final int KEY_CLEAR = 14;

	// TextView
	private TextView mTextView;

	// �O�̏���
	int beforeStatus = 0;

	// �v�Z���̒l
	private ArrayList<String> calcArray;

	// �v�Z���鎞�̔z��
	private ArrayList<String> signArray;

	// ���v
	int total = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// �\���pTextView
		mTextView = (TextView) findViewById(R.id.display);

		// Button
		mButton = new Button[mId.length];

		// Button�̎�荞�݂ƃC�x���g�̂͂��
		for (int i = 0; i < mId.length; i++) {
			// button����荞��
			mButton[i] = (Button) findViewById(mId[i]);
			// button�̃C�x���g����
			mButton[i].setOnClickListener(this);
		}
		calcArray = new ArrayList<String>();
		signArray = new ArrayList<String>();
	}

	@Override
	public void onClick(View view) {

		// �����ꂽ�{�^�����ǂ̃{�^�����𔻒�
		for (int i = 0; i < mId.length; i++) {
			if (view.equals(mButton[i])) {
				String nowValue = mTextView.getText().toString();
				// CLEAR
				if (i == KEY_CLEAR) {
					mTextView.setText("");
					calcArray.clear();
					signArray.clear();
					beforeStatus = KEY_CLEAR;
				}
				// =
				else if (i == KEY_EQUAL && nowValue.length() > 0) {
					nowValue = checkDisplay(nowValue);
					calcArray.add(nowValue);
					double ans = calc();				
					mTextView.setText(Double.toString(ans));
					calcArray.clear();
					signArray.clear();
					beforeStatus = i;
				}

				// +
				else if (i == KEY_PLUS && nowValue.length() > 0) {
					calcArray.add(nowValue);
					signArray.add("+");
					beforeStatus = KEY_PLUS;
				}
				// -
				else if (i == KEY_MINUS && nowValue.length() > 0) {
					calcArray.add(nowValue);
					signArray.add("-");
					beforeStatus = KEY_MINUS;
				}
				// .
				else if (i == KEY_TEN) {
					// .�L�[�����������A���Z�L�[��������Ă����ꍇ
					nowValue = checkDisplay(nowValue);
					// �����Ȃ�.�L�[�������ꂽ�ꍇ
					if (nowValue.length() == 0) {
						nowValue = "0.";
					} else {
						nowValue = nowValue + ".";
					}
					mTextView.setText(nowValue);
					beforeStatus = i;
				}
				// ����
				else if (i < 10) {
					nowValue = checkDisplay(nowValue);
					// 0�������͂���Ă��Ȃ��ꍇ��0���Q�ȏ���΂Ȃ��悤�ɂ���
					if (nowValue.equals("0") && i == 0) {
						return;
					} else if (nowValue.equals("0") && i != 0) {
						nowValue = "";
					}

					nowValue = nowValue + i;
					mTextView.setText(nowValue);
					beforeStatus = i;
				}
				break;
			}
		}
	}

	//���Z���s���Ă����ꍇ���m�F����
	//�x�ꂽ��ԂŃf�B�X�v���C�̒l������������
	private String checkDisplay(String now) {
		if (beforeStatus == KEY_PLUS 
				|| beforeStatus == KEY_MINUS 
					|| beforeStatus == KEY_EQUAL
				) {
			mTextView.setText("");
			return "0";
		}
		return now;
	}
	// �v�Z����
	private double calc() {
		if (calcArray.size() == 0) {
			return 0.0;
		}
		if (calcArray.size() == 1) {
			return Double.parseDouble(calcArray.get(0));
		}
		double passive = Double.parseDouble(calcArray.get(0));
		double active;
		int j = 0;
		for (int i = 1; i < calcArray.size(); i++) {
			active = Double.parseDouble(calcArray.get(i));
			if (signArray.get(j).equals("+")) {
				passive += active;
			} else {
				passive -= active;
			}
			j++;
		}
		return passive;
	}

}
