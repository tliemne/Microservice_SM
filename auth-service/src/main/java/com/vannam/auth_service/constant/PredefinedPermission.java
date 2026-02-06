package com.vannam.auth_service.constant;

import java.util.HashSet;
import java.util.Set;

public class PredefinedPermission {
    public static final Set<String> STUDENT_PERMISSION =
            Set.of("STUDENT_READ", "STUDENT_UPDATE");

    public static final Set<String> ADMIN_PERMISSION =
            Set.of("STUDENT_READ", "STUDENT_UPDATE","STUDENT_CREATE","STUDENT_DELETE","CLASS_UPDATE","CLASS_READ",
                    "CLASS_CREATE","CLASS_DELETE","SCHOOL_UPDATE","SCHOOL_CREATE","SCHOOL_DELETE","SCHOOL_READ");
    public static final Set<String> SCHOOL_MANAGER_PERMISSION =
            Set.of("STUDENT_READ", "STUDENT_UPDATE","STUDENT_CREATE","STUDENT_DELETE","CLASS_UPDATE","CLASS_READ",
                    "CLASS_CREATE","CLASS_DELETE","SCHOOL_UPDATE","SCHOOL_READ");



    private PredefinedPermission() {}
}
