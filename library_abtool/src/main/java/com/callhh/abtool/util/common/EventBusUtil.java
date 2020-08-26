package com.callhh.abtool.util.common;

import org.greenrobot.eventbus.EventBus;

/**
 * EventBus是Android下高效的发布/订阅事件总线机制。
 * 作用是可以代替传统的Intent,Handler,Broadcast或接口函数在Fragment,Activity,Service,线程之间传递数据，执行方法。
 * 特点是代码简洁，是一种发布订阅设计模式（Publish/Subsribe），或称作观察者设计模式
 */
public class EventBusUtil {

	private EventBusUtil() {
	}
	/**
	 * 注册EventBus
	 * 注册EventBus  A界面  onCreate方法中注册
	 * 注意：最好是放在初始化组件之后，不然页面可能会接收不到参数
	 * @param subscriber 订阅者对象
	 */
	public static void register(Object subscriber) {
		if (!EventBus.getDefault().isRegistered(subscriber)) {
			EventBus.getDefault().register(subscriber);
		} else {
		}
	}

	/**
	 * 取消注册EventBus
	 * 解除EventBus的注册 A界面 onDestory方法中解除
	 * @param subscriber 订阅者对象
	 */
	public static void unregister(Object subscriber) {
		EventBus.getDefault().unregister(subscriber);
	}

	/**
	 * 发布订阅事件
	 * 分发事件:使用对象传递数据  B界面   需要传递的地方写
	 * @param event 事件对象
	 */
	public static void post(Object event) {
		EventBus.getDefault().post(event);
	}

	/**
	 * 发布粘性订阅事件（即发送置顶事件）
	 * 主界面发送消息给主界面和二界面，注意接收的时候需要增加sticky = true，如@Subscribs(Sticky =true)表明处理置顶事件
	 * @param event 事件对象
	 */
	public static void postSticky(Object event) {
		EventBus.getDefault().postSticky(event);
	}

	/**
	 * 移除指定的粘性订阅事件
	 *
	 * @param eventType class的字节码，例如：String.class
	 */
	public static <T> void removeStickyEvent(Class<T> eventType) {
		T stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
		if (stickyEvent != null) {
			EventBus.getDefault().removeStickyEvent((T) stickyEvent);
		}
	}

	/**
	 * 移除所有的粘性订阅事件
	 */
	public static void removeAllStickyEvents() {
		EventBus.getDefault().removeAllStickyEvents();
	}

	/**
	 * 取消事件传送
	 *
	 * @param event 事件对象
	 */
	public static void cancelEventDelivery(Object event) {
		EventBus.getDefault().cancelEventDelivery(event);
	}

}
