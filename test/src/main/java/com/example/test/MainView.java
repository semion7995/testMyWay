package com.example.test;

import com.example.test.domain.Pojo;
import com.example.test.repo.PojoRepository;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("main")
public class MainView extends VerticalLayout {
    //генерирую объект репозитория, чтобы сохранять данные в базу
    @Autowired
    private PojoRepository pojoRepository;
    //создаю экземпляр com.vaadin.flow.data.binder.Binder для связывания полей пользовательского интерфейса с объектом предметной области
    private Binder<Pojo> binder = new Binder<>(Pojo.class);
    //создаю объект текстового поля пользовательского интерфейса
    private TextField textField = new TextField("Сlick on the button or enter a value!!!");

    public MainView() {
        //описываю в методе связь полей пользовательского интерфейса с объектом предметной области
        bindAUserInterfaceFormToADomainModelObject();
        //получаю "" пустую строку изначального значения текстового поля и устанавливаю значение 0 по умолчанию
        String value = textField.getValue();
        if (value.equals("")) {
            textField.setValue("0");
        }
        //создаю объект кнопки
        Button btn = new Button("Click me");
        //вешаю слушатель на кнопку, чтобы ловить нажатия и обрабатывать логику после клика
        btn.addClickListener(buttonClickEvent -> {
            //считываю текущее значение с поля пользовательского интерфейса использую Binder
            Pojo writePojo = writeFieldPojo(new Pojo());
            //увеличиваю значение текстового поля на единицу
            Integer integer = Integer.valueOf(writePojo.getCount()) + 1;
            //обновляю значение поля в объекте
            writePojo.setCount(integer.toString());
            //вывожу значение в текстовое поле пользовательского интерфейса
            textField.setValue(writePojo.getCount());
        });
        //отслеживаю фокус на текстовом поле
        textField.focus();
        //вешаю слушатель на текстовое поле, при потере фокуса на котором произойдет сохранение значения в Базе Данных
        textField.addValueChangeListener(new HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<TextField, String>>() {
            @Override
            public void valueChanged(AbstractField.ComponentValueChangeEvent<TextField, String> textFieldStringComponentValueChangeEvent) {
                //считываю значение текстового поля с пользовательского интерфейса
                Pojo writePojo = writeFieldPojo(new Pojo());
                //сохраняю значение в Базе Данных
                pojoRepository.save(writePojo);
            }
        });
        //Добавляю объект текстового поля и объект кнопки на фронтент
        add(textField);
        add(btn);
    }

    /**
     * Accepts an empty Pojo object, fills it with
     * the value of the user interface text field,
     * and returns the filled object
     * @param pojoModifiedTextField
     * @return Pojo the read object from the user interface
     */
    private Pojo writeFieldPojo(Pojo pojoModifiedTextField) {
        try {
            binder.writeBean(pojoModifiedTextField);
            return pojoModifiedTextField;
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * I describe the relationship of the user interface with the object of the subject area
     */
    private void bindAUserInterfaceFormToADomainModelObject() {
        binder.bind(textField, pojo -> pojo.getCount(), (pojo, count) -> {
            pojo.setCount(count);
        });
    }
}
