package org.wx.oa.entity;
/**
 *  音乐消息实体封装
 * */
public class MusicMessage extends BaseMessage{
    private Music music;

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }
}
