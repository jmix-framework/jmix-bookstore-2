package io.jmix.bookstore.security;

import io.jmix.core.entity.KeyValueEntity;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.model.SecurityScope;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "Flow UI: minimal access", code = UiMinimalRole.CODE, scope = SecurityScope.UI)
public interface UiMinimalRole {

    String CODE = "flowui-minimal";

    @ViewPolicy(viewIds = "bookstore_MainView")
    void main();

    @ViewPolicy(viewIds = "bookstore_LoginView")
    @SpecificPolicy(resources = "ui.loginToUi")
    void login();

    @EntityPolicy(entityClass = KeyValueEntity.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = KeyValueEntity.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void keyValueEntity();
}
