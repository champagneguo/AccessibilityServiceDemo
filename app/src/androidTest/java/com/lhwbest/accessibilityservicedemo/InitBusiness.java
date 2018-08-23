package com.lhwbest.accessibilityservicedemo;

import com.sina.weibo.greenlight.base.BaseBusiness;
import com.sina.weibo.greenlight.context.CoreContext;
import com.sina.weibo.greenlight.uiautomator.UiSelector;
import com.sina.weibo.greenlight.wbwidget.selector.Ui;
import com.sina.weibo.greenlight.wbwidget.view.WBView;

/**
 * Created by xiangbin1 on 2018/7/24.
 */
public class InitBusiness extends BaseBusiness {

    public InitBusiness(CoreContext coreContext) {
        super(coreContext);
    }

    public void init_001() {
        coreContext.maxSleep();
        coreContext.clickView(Ui.res("com.lhwbest.accessibilityservicedemo", "btn_status"));
        coreContext.maxSleep();
        coreContext.clickView(Ui.text("微博自动化辅助服务"));
        coreContext.maxSleep();
        UiSelector uiSelector = Ui.textContains("微博自动化测试辅助工具,");
        WBView wbView = coreContext.getView(uiSelector);
        coreContext.clickOnScreen(wbView.getX() + wbView.getWidth() / 2, wbView.getY() - 20);
//        coreContext.clickView(Ui.res("com.android.settings", "title"));
//        coreContext.maxSleep();
//        coreContext.clickView(Ui.text("确定"));
//        coreContext.clickView(Ui.textContains("开启"));
        coreContext.maxSleep();
        coreContext.clickView(Ui.text("确定"));
        coreContext.maxSleep();
    }
}
