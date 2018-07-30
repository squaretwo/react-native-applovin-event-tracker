package com.squaretwo.applovin.eventtracker;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import android.content.Intent;
import android.util.Log;
import com.applovin.sdk.*;

import java.util.Map;
import java.util.HashMap;

public class RNApplovinEventTrackerModule extends ReactContextBaseJavaModule implements ActivityEventListener {

  final String TAG = "RNApplovinEventTrackerModule";
  Intent lastPurchaseIntent = null;

  public RNApplovinEventTrackerModule(ReactApplicationContext reactContext) {
    super(reactContext);
    reactContext.addActivityEventListener(this);
  }

  @ReactMethod
  public void loginEvent(String userId) {
    final AppLovinEventService eventService = AppLovinSdk.getInstance(getCurrentActivity()).getEventService();

    final Map<String, String> parameters = new HashMap<String, String>();
    parameters.put(AppLovinEventParameters.USER_ACCOUNT_IDENTIFIER, userId);

    eventService.trackEvent(AppLovinEventTypes.USER_LOGGED_IN, parameters);
    Log.d(TAG, "loginEvent userId:" + userId);
  }

  @ReactMethod
  public void inAppPurchaseEvent(String amountOfMoneySpent, String currency, ReadableMap detail) {
    final AppLovinEventService eventService = AppLovinSdk.getInstance(getCurrentActivity()).getEventService();

    final Map<String, String> parameters = new HashMap<String, String>();
    parameters.put(AppLovinEventParameters.REVENUE_AMOUNT, amountOfMoneySpent);
    parameters.put(AppLovinEventParameters.REVENUE_CURRENCY, currency);

    eventService.trackInAppPurchase(this.lastPurchaseIntent, parameters);
    Log.d(TAG, "loginEvent amountOfMoneySpent:" + amountOfMoneySpent + ", currency:" + currency + ", intent:" + this.lastPurchaseIntent.toString());
  }

  @Override
  public void onActivityResult(final Activity activity, final int requestCode, final int resultCode, final Intent intent) {
    this.lastPurchaseIntent = intent;
  }

  @Override
  public void onNewIntent(Intent intent){

  }

  @ReactMethod
  public void invitationEvent() {
    final AppLovinEventService eventService = AppLovinSdk.getInstance(getCurrentActivity()).getEventService();
    eventService.trackEvent(AppLovinEventTypes.USER_SENT_INVITATION);
    Log.d(TAG, "invitationEvent");
  }

  @ReactMethod
  public void spentVirtualCurrencyEvent(String currencyAmount, String currencyName) {
    final AppLovinEventService eventService = AppLovinSdk.getInstance(getCurrentActivity()).getEventService();

    final Map<String, String> parameters = new HashMap<String, String>();
    parameters.put(AppLovinEventParameters.VIRTUAL_CURRENCY_AMOUNT, currencyAmount);
    parameters.put(AppLovinEventParameters.VIRTUAL_CURRENCY_NAME, currencyName);

    eventService.trackEvent(AppLovinEventTypes.USER_SPENT_VIRTUAL_CURRENCY, parameters);
    Log.d(TAG, "spentVirtualCurrencyEvent currencyAmount:" + currencyAmount + ", currencyName:" + currencyName);
  }

  @Override
  public String getName() {
    return "RNApplovinEventTracker";
  }
}