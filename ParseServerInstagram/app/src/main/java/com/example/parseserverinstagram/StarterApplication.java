/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.example.parseserverinstagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;



public class StarterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("c97edade7437e741f2d02deb5cba037593bd967b")
                .clientKey("a781e2beb32a3b1c378878bb78c34040f9648af5")
                .server("http://ec2-13-58-28-113.us-east-2.compute.amazonaws.com:80/parse/")
                .build()
        );



//signs users up auto without username and password
        // ParseUser.enableAutomaticUser();

        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
