package entity;

import java.io.Serializable;

public class HeartBeatPack implements Serializable {
    private byte alive;

    public HeartBeatPack(byte alive) {
        this.alive = alive;
    }

    public byte getAlive() {
        return alive;
    }

    public void setAlive(byte alive) {
        this.alive = alive;
    }
}
