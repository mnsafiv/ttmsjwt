package ru.safonoviv.ttms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import ru.safonoviv.ttms.util.ReadJson;

@OpenAPIDefinition
@Configuration
public class SpringDocConfig {
	
	@Autowired
	private ReadJson readJson;
	
	@Bean
	public OpenAPI baseOpenApi() {		
		ApiResponse successfulАuthorization = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("autorizedResponse").toString()))))
				.description("Successful authorization");

		ApiResponse unautorized = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("unautorizedResponse").toString()))))
				.description("Unautorized");

		ApiResponse registerBadRequest = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("registerBadRequest").toString()))))
				.description("Fail register");

		ApiResponse successfulRegister = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("registerResponse").toString()))))
				.description("Successful register");

		ApiResponse forbidden = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("forbiddenResponse").toString()))))
				.description("forbidden");

		ApiResponse findAllTasks = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("taskFindResponse").toString()))))
				.description("Find all tasks");
		
		ApiResponse findAllTasksEmpty = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("taskFindEmptyResponse").toString()))))
				.description("Find all tasks");
		
		ApiResponse findAuthorTasks = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("authorTaskFindResponse").toString()))))
				.description("Find all tasks by author");
		
		ApiResponse findAuthorTasksEmpty = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("authorTaskFindEmptyResponse").toString()))))
				.description("Find all tasks by author");
		
		
		ApiResponse findPerformerTasks = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("performerTaskFindResponse").toString()))))
				.description("Find all tasks by performer");
		
		ApiResponse findPerformerTasksEmpty = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("performerTaskFindEmptyResponse").toString()))))
				.description("Find all tasks by performer");
		
		ApiResponse changeTaskStatusSuccess = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("changeTaskStatusResponse").toString()))))
				.description("Change status");
		
		ApiResponse updateTaskSuccess = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("updateTaskResponse").toString()))))
				.description("Update status");
		
		ApiResponse badRequest = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("badRequest").toString()))))
				.description("Bad request");
		
		ApiResponse deleteTaskSuccess = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("deleteTaskResponse").toString()))))
				.description("Delete task");
		
		ApiResponse createTaskSuccess = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("createTaskResponse").toString()))))
				.description("Create task");
		
		ApiResponse addCommentSuccess = new ApiResponse()
				.content(new Content().addMediaType(MediaType.APPLICATION_JSON_VALUE,
						new io.swagger.v3.oas.models.media.MediaType().addExamples("default",
								new Example().value(readJson.read().get("addCommentResponse").toString()))))
				.description("Add comment to task");
		
		
		
		Components components = new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme());
		components.addResponses("successfulАuthorization", successfulАuthorization);
		components.addResponses("unautorized", unautorized);
		components.addResponses("badRequest", badRequest);
		
		components.addResponses("registerBadRequest", registerBadRequest);
		components.addResponses("successfulRegister", successfulRegister);
		
		components.addResponses("forbidden", forbidden);
		
		components.addResponses("findAllTasks", findAllTasks);
		components.addResponses("findAllTasksEmpty", findAllTasksEmpty);
		
		components.addResponses("findAuthorTasks", findAuthorTasks);
		components.addResponses("findAuthorTasksEmpty", findAuthorTasksEmpty);
		
		components.addResponses("findPerformerTasks", findPerformerTasks);
		components.addResponses("findPerformerTasksEmpty", findPerformerTasksEmpty);
		
		components.addResponses("changeTaskStatusSuccess", changeTaskStatusSuccess);
		
		components.addResponses("deleteTaskSuccess", deleteTaskSuccess);
		components.addResponses("updateTaskSuccess", updateTaskSuccess);
		components.addResponses("createTaskSuccess", createTaskSuccess);
		
		components.addResponses("addCommentSuccess", addCommentSuccess);
		
		
		
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
				.components(components)
				.info(new Info().title("Spring doc").version("1.0.0").description("Spring doc"));
	}
	
	
	private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer");
    }

}
