package com.flowforge.chain;

import com.flowforge.repository.ProjectRepository;
import org.springframework.stereotype.Component;

@Component
public class ProjectValidationHandler
        extends TaskValidationHandler {

    private final ProjectRepository projectRepository;

    public ProjectValidationHandler(
            ProjectRepository projectRepository
    ) {
        this.projectRepository = projectRepository;
    }

    @Override
    protected void validate(
            TaskValidationContext context
    ) {

        Long projectId =
                context.getRequest().getProjectId();

        if (projectId != null &&
                !projectRepository.existsById(projectId)) {

            throw new RuntimeException(
                    "Project not found"
            );
        }
    }
}