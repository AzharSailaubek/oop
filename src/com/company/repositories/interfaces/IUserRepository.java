package com.company.repositories.interfaces;
import com.company.models.User;

public interface IUserRepository {
    User authenticate(String username, String password);
}