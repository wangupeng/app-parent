package com.cars.core.yzm;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

/**
 * Created by wangyupeng on 2018/6/29 18:02
 * 提供了判断仓库中是否有相应的验证码存在。
 */
public class MyManageableImageCaptchaService extends DefaultManageableImageCaptchaService {
    public MyManageableImageCaptchaService(CaptchaStore captchaStore, CaptchaEngine captchaEngine,int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize, int captchaStoreLoadBeforeGarbageCollection) {
        super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }

    public boolean hasCapcha(String id, String userCaptchaResponse) {
        return store.getCaptcha(id).validateResponse(userCaptchaResponse);
    }
}
