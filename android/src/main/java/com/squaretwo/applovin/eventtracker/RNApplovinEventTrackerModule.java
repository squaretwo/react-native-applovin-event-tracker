package com.squaretwo.applovin.eventtracker;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import android.content.Intent;
import com.applovin.sdk.*;

import java.util.Map;
import java.util.HashMap;

public class RNApplovinEventTrackerModule extends ReactContextBaseJavaModule {


  public RNApplovinEventTrackerModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @ReactMethod
  public void loginEvent(String userId) {
    final AppLovinEventService eventService = AppLovinSdk.getInstance(getCurrentActivity()).getEventService();

    final Map<String, String> parameters = new HashMap<String, String>();
    parameters.put(AppLovinEventParameters.USER_ACCOUNT_IDENTIFIER, userId);

    eventService.trackEvent(AppLovinEventTypes.USER_LOGGED_IN, parameters);
  }

  @ReactMethod
  public void inAppPurchaseEvent(String amountOfMoneySpent, String currency, ReadableMap detail) {
    final AppLovinEventService eventService = AppLovinSdk.getInstance(getCurrentActivity()).getEventService();

    final Map<String, String> parameters = new HashMap<String, String>();
    parameters.put(AppLovinEventParameters.REVENUE_AMOUNT, amountOfMoneySpent);
    parameters.put(AppLovinEventParameters.REVENUE_CURRENCY, currency);

    HashMap<String, String> purchaseData = new HashMap<String, String>();
    purchaseData.put("productId", detail.getString("productId"));
    purchaseData.put("orderId", detail.getString("productId"));
    purchaseData.put("purchaseToken", detail.getString("productId"));
    purchaseData.put("purchaseTime", detail.getString("purchaseTime"));
    purchaseData.put("purchaseState", detail.getString("purchaseState"));

    Intent intent = new Intent();
    intent.putExtra("INAPP_PURCHASE_DATA", purchaseData);
    intent.putExtra("INAPP_DATA_SIGNATURE", detail.getString("receiptSignature"));

    eventService.trackInAppPurchase(intent, parameters);
  }

  @ReactMethod
  public void invitationEvent() {
    final AppLovinEventService eventService = AppLovinSdk.getInstance(getCurrentActivity()).getEventService();
    eventService.trackEvent(AppLovinEventTypes.USER_SENT_INVITATION);
  }

  @ReactMethod
  public void spentVirtualCurrencyEvent(String currencyAmount, String currencyName) {
    final AppLovinEventService eventService = AppLovinSdk.getInstance(getCurrentActivity()).getEventService();

    final Map<String, String> parameters = new HashMap<String, String>();
    parameters.put(AppLovinEventParameters.VIRTUAL_CURRENCY_AMOUNT, currencyAmount);
    parameters.put(AppLovinEventParameters.VIRTUAL_CURRENCY_NAME, currencyName);

    eventService.trackEvent(AppLovinEventTypes.USER_SPENT_VIRTUAL_CURRENCY, parameters);
  }

  @Override
  public String getName() {
    return "RNApplovinEventTracker";
  }
}