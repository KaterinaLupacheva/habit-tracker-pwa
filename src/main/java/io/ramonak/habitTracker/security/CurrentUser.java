package io.ramonak.habitTracker.security;

import io.ramonak.habitTracker.entity.User;

@FunctionalInterface
public interface CurrentUser {

	User getUser();
}