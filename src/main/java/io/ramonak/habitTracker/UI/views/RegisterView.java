package io.ramonak.habitTracker.UI.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import io.ramonak.habitTracker.entity.User;
import io.ramonak.habitTracker.service.UserService;

@Route(value = RegisterView.ROUTE)
@PageTitle("Register")
class RegisterView extends VerticalLayout {

   public static final String ROUTE = "register";

    public RegisterView(UserService userService) {
        setSizeFull();
        Binder<User> binder = new Binder<>();
        TextField username = new TextField("Username");
        username.setRequired(true);
        binder.forField(username)
                .asRequired()
                .withValidator(new StringLengthValidator("Min length 5 characters", 5, 255))
                .bind(User::getUsername, User::setUsername);
        EmailField email = new EmailField("Email");
        email.setRequiredIndicatorVisible(true);
        binder.forField(email)
                .asRequired()
                .withValidator(new EmailValidator("Not valid email"))
                .bind(User::getEmail, User::setEmail);
        PasswordField passwordField = new PasswordField("Password");
        binder.forField(passwordField)
                .asRequired()
                .bind(User::getPasswordHash, User::setPasswordHash);
        passwordField.setRequired(true);
        Button register = new Button("Register");
        User user = new User();
        binder.readBean(user);
        register.addClickListener(e -> {
            try {
                binder.writeBean(user);
                userService.registerNewUser(user);
            } catch (ValidationException ex) {
                ex.getValidationErrors();
            }
        });

        add(username, email, passwordField, register);
        setAlignItems(Alignment.CENTER);
    }

//    @Override
//    public void configurePage(InitialPageSettings settings) {
//        settings.addInlineWithContents(
//                InitialPageSettings.Position.PREPEND, "window.customElements=window.customElements||{};"
//                        + "window.customElements.forcePolyfill=true;" + "window.ShadyDOM={force:true};",
//                InitialPageSettings.WrapMode.JAVASCRIPT);
//    }
}
