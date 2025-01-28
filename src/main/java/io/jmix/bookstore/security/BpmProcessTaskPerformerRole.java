package io.jmix.bookstore.security;

import io.jmix.bpm.entity.ContentStorage;
import io.jmix.bpm.entity.ProcessDefinitionData;
import io.jmix.bpm.entity.ProcessInstanceData;
import io.jmix.bpm.entity.TaskData;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.model.SecurityScope;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "BPM: process task performer", code = BpmProcessTaskPerformerRole.CODE, scope = SecurityScope.UI)
public interface BpmProcessTaskPerformerRole {
    String CODE = "bpm-process-task-performer";

    @ViewPolicy(viewIds = {
            "bookstore_MyProcessTasksListView",
            "bpm_DefaultStartProcessForm",
            "bpm_DefaultTaskProcessForm",
            "bpm_InputDialogStartProcessForm",
            "bpm_InputDialogTaskProcessForm"
    })
    @EntityPolicy(entityClass = ContentStorage.class, actions = {EntityPolicyAction.READ})
    @EntityPolicy(entityClass = ProcessDefinitionData.class, actions = {EntityPolicyAction.READ})
    @EntityPolicy(entityClass = ProcessInstanceData.class, actions = {EntityPolicyAction.READ})
    @EntityPolicy(entityClass = TaskData.class, actions = {EntityPolicyAction.READ})
    @EntityAttributePolicy(entityClass = ContentStorage.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityAttributePolicy(entityClass = ProcessDefinitionData.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityAttributePolicy(entityClass = TaskData.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void bpmProcessTaskPerformer();
}
