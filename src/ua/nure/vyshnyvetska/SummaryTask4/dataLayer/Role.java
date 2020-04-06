package ua.nure.vyshnyvetska.SummaryTask4.dataLayer;

import ua.nure.vyshnyvetska.SummaryTask4.dataLayer.entity.User;

public enum Role {
    ADMIN, USER;
    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

}