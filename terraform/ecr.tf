# Repositório ECR
resource "aws_ecr_repository" "auth_api" {
  name = "${var.project_name}-${var.environment}"
  
  image_scanning_configuration {
    scan_on_push = true
  }

  image_tag_mutability = "MUTABLE"

  tags = {
    Name = "${var.project_name}-ecr"
  }
}

# Saída com URI do repositório
output "ecr_repository_url" {
  description = "URL do repositório ECR"
  value       = aws_ecr_repository.auth_api.repository_url
}
