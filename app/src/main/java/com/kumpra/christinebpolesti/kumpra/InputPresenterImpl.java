package com.kumpra.christinebpolesti.kumpra;

/**
 * Created by christine B. Polesti on 7/3/2015.
 */
public class InputPresenterImpl implements InputPresenter, OnInputFinishedListener {

    private InputView inputView;
    private InputInteractor inputInteractor;;

    public InputPresenterImpl(InputView inputView) {
        this.inputView = inputView;
        this.inputInteractor = inputInteractor;
    }

    @Override
    public void onItemError() {
        inputView.setItemError();
    }

    @Override
    public void onQtyError() {
        inputView.setQtyError();
    }

    public void onSuccess() {
        inputView.navigateToHistory();
    }

    @Override
    public void saveItem(String item, String qty) {
        inputInteractor.input(item, qty, this);
    }
}
