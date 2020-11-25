package com.wy.letsgo.biz;

import com.wy.letsgo.biz.implAsmack.GroupChatBiz;
import com.wy.letsgo.biz.implAsmack.LoginBiz;

public class Factory {
public static ILoginBiz getLoginBizInstance()
{
	return new LoginBiz();
}

public static IGroupChatBiz getGroupChatBizInstance()
{
	return new GroupChatBiz();
	}
}
