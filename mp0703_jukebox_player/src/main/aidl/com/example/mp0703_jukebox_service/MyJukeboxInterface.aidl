// MyJukeboxInterface.aidl
package com.example.mp0703_jukebox_service;

// Declare any non-default types here with import statements

interface MyJukeboxInterface {
    void start();
    void stop();
    int getMaxDuration();
}