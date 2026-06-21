// MyJukeboxInterface.aidl
package com.example.mp0703_jukebox_service;

interface MyJukeboxInterface {
    void start(int songId);
    void stop();
    int getMaxDuration();
}
