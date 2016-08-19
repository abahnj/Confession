package com.abahnj.confession;

import com.github.orangegangsters.lollipin.lib.managers.AppLock;
import com.github.orangegangsters.lollipin.lib.managers.AppLockActivity;

/**
 * Created by abahnj on 8/11/2016.
 */
public class ConfessionPinActivity extends AppLockActivity {


    @Override
    public int getContentView() {
        return R.layout.activity_pin_code;
    }

    @Override
    public void showForgotDialog() {

    }

    @Override
    public void onPinFailure(int attempts) {

    }

    @Override
    public String getStepText(int reason) {
        switch (reason){
            case AppLock.UNLOCK_PIN:
                return "Passcode";
            default:
                return super.getStepText(reason);
        }
    }

    @Override
    public void onPinSuccess(int attempts) {

    }


}
