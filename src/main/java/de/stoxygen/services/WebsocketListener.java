package de.stoxygen.services;

public interface WebsocketListener {
    public void handleData(String msg);

    public void handleData(String msg, Integer exchangesId);
}
