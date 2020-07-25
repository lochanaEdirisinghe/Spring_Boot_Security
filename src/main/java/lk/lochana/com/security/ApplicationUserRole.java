package lk.lochana.com.security;

import com.google.common.collect.Sets;

import java.util.Set;

import static lk.lochana.com.security.AppUserPermissions.*;

public enum ApplicationUserRole {

    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE));

    private Set<AppUserPermissions> permissions;

    ApplicationUserRole(Set<AppUserPermissions> permissions) {
        this.permissions = permissions;
    }

    public Set<AppUserPermissions> getPermissions() {
        return permissions;
    }
}
