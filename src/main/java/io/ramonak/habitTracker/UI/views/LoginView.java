package io.ramonak.habitTracker.UI.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Route(value = LoginView.ROUTE)
@PageTitle("Login")
public class LoginView extends VerticalLayout {
    public static final String ROUTE = "login";

    public LoginView() {
        LoginForm login = new LoginForm();
        login.setAction("login"); //
//        getElement().appendChild(login.getElement()); //
//        login.addLoginListener(e -> {
//            UI.getCurrent().navigate("habit");
//        });
        Button register = new Button("Register", e-> UI.getCurrent().navigate("register"));
        add(login, register);
        setAlignItems(Alignment.CENTER);


//        login.addLoginListener(e -> { //
//            try {
//                // try to authenticate with given credentials, should always return not null or throw an {@link AuthenticationException}
//                final Authentication authentication = authenticationManager
//                        .authenticate(new UsernamePasswordAuthenticationToken(e.getUsername(), e.getPassword())); //
//
//                // if authentication was successful we will update the security context and redirect to the page requested first
//                SecurityContextHolder.getContext().setAuthentication(authentication); //
////                login.close(); //
//                UI.getCurrent().navigate(requestCache.resolveRedirectUrl()); //
//
//            } catch (AuthenticationException ex) { //
//                // show default error message
//                // Note: You should not expose any detailed information here like "username is known but password is wrong"
//                // as it weakens security.
//                login.setError(true);
//            }
//        });
    }

//    @Override
//    public void beforeEnter(BeforeEnterEvent event) {
//        // inform the user about an authentication error
//        // (yes, the API for resolving query parameters is annoying...)
//        if(!event.getLocation().getQueryParameters().getParameters().getOrDefault("error", Collections.emptyList()).isEmpty()) {
//            login.setError(true); //
//        }
//    }
}
