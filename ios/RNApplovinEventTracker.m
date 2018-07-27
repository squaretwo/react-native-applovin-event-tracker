//
//  RNApplovinEventTracker.m
//
//  Created by Mingyu Kang on 7/25/18.
//  Copyright © 2018 SquareTwo. All rights reserved.
//

#ifndef RNAdcolonyEventTracking_h
#define RNAdcolonyEventTracking_h


#endif /* RNAdcolonyEventTracker_h */
#import "RNApplovinEventTracker.h"
#import "ISAppLovinAdapter/ALSdk.h"

@implementation RNApplovinEventTracker

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(loginEvent:(NSString *)userId)
{
    ALEventService* eventService = [ALSdk shared].eventService;
    
    [eventService trackEvent: kALEventTypeUserLoggedIn
                  parameters: @{kALEventParameterUserAccountIdentifierKey : userId}
    ];
}

RCT_EXPORT_METHOD(inAppPurchaseEvent:(NSString *)transactionIdentifier productIdentifier:(NSString *)productIdentifier amountOfMoneySpent:(NSString *)amountOfMoneySpent currency:(NSString *)currency)
{
    ALEventService* eventService = [ALSdk shared].eventService;
    [eventService trackInAppPurchaseWithTransactionIdentifier: transactionIdentifier
                                               parameters: @{
                                                             kALEventParameterRevenueAmountKey : amountOfMoneySpent,
                                                             kALEventParameterRevenueCurrencyKey : currency,
                                                             kALEventParameterProductIdentifierKey : productIdentifier
                                                             }
     ];
}

RCT_EXPORT_METHOD(invitationEvent)
{
    ALEventService* eventService = [ALSdk shared].eventService;
    
    [eventService trackEvent: kALEventTypeUserSentInvitation];
}

RCT_EXPORT_METHOD(spentVirtualCurrencyEvent:(NSString *)currencyAmount currencyName:(NSString *)currencyName)
{
    ALEventService* eventService = [ALSdk shared].eventService;
    
    [eventService trackEvent: kALEventTypeUserSpentVirtualCurrency
                  parameters: @{
                                kALEventParameterVirtualCurrencyAmountKey : currencyAmount,
                                kALEventParameterVirtualCurrencyNameKey : currencyName
                                }
     ];
}

@end
