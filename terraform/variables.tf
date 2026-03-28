variable "aws_region" {
  description = "AWS Region"
  type        = string
  default     = "us-east-1"
}

variable "project_name" {
  description = "Nome do projeto"
  type        = string
  default     = "auth-api"
}

variable "environment" {
  description = "Ambiente (dev, staging, prod)"
  type        = string
  default     = "dev"
}

variable "app_port" {
  description = "Porta da aplicação"
  type        = number
  default     = 8080
}

variable "ecs_desired_count" {
  description = "Número de instâncias da API"
  type        = number
  default     = 1
}

variable "ecs_cpu" {
  description = "CPU da task (unidades 1024 = 1 vCPU)"
  type        = number
  default     = 512
}

variable "ecs_memory" {
  description = "Memória da task (MB)"
  type        = number
  default     = 1024
}

variable "jwt_issuer" {
  description = "Issuer do JWT"
  type        = string
  default     = "auth-api"
}
