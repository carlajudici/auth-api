output "ecr_repository_url" {
  description = "URL do repositório ECR"
  value       = aws_ecr_repository.auth_api.repository_url
}

output "ecs_cluster_name" {
  description = "Nome do cluster ECS"
  value       = aws_ecs_cluster.main.name
}

output "ecs_service_name" {
  description = "Nome do serviço ECS"
  value       = aws_ecs_service.auth_api.name
}

output "ecs_task_definition_arn" {
  description = "ARN da task definition"
  value       = aws_ecs_task_definition.auth_api.arn
}

output "security_group_id" {
  description = "ID do Security Group"
  value       = aws_security_group.auth_api.id
}

output "cloudwatch_log_group" {
  description = "Nome do grupo de logs"
  value       = aws_cloudwatch_log_group.auth_api.name
}
