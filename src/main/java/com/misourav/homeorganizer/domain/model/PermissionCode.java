package com.misourav.homeorganizer.domain.model;

/**
 * Canonical catalog of permissions. Kept as an enum so the domain can reference them
 * directly and the compiler catches typos. The persistence layer stores them by name().
 */
public enum PermissionCode {
    // Expenses
    EXPENSE_CREATE_OWN,
    EXPENSE_CREATE_ANY,
    EXPENSE_EDIT_ANY,
    EXPENSE_DELETE,
    EXPENSE_VIEW_OWN,
    EXPENSE_VIEW_ANY,

    // Meal management
    MEAL_ENTER_SELF,
    MEAL_ENTER_ANY,
    MEAL_CLOSE,
    BAZAAR_ENTER,

    // Tasks
    TASK_VIEW,
    TASK_CREATE,
    TASK_ASSIGN,
    TASK_COMPLETE_OWN,
    TASK_COMPLETE_ANY,

    // Bills
    BILL_VIEW,
    BILL_CREATE,
    BILL_DELETE,

    // Shopping & household
    SHOPPING_MANAGE,
    BUDGET_MANAGE,
    HOUSEHOLD_SETTINGS,

    // Members & roles
    MEMBER_INVITE,
    MEMBER_REMOVE,
    ROLE_CHANGE,

    // Chat & announcements
    CHAT_SEND,
    ANNOUNCEMENT_POST,

    // Reports
    REPORT_VIEW_OWN,
    REPORT_VIEW_HOUSEHOLD
}
