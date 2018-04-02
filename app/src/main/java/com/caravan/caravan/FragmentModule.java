package com.caravan.caravan;

import com.amazonaws.mobile.client.AWSMobileClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    @Provides
    @Singleton
    static AWSMobileClient provideAWSMobileClient() {
        return AWSMobileClient.getInstance();
    }

    @Provides
    static AccountFragment provideAccountFragement() {
        return new AccountFragment();
    }

    @Provides
    static HomeFragment provideHomeFragment() {
        return new HomeFragment();
    }
}
