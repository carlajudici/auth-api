terraform {
  backend "s3" {
    bucket         = "auth-api-terraform-state"
    key            = "terraform.tfstate"
    region         = "us-east-1"
    encrypt        = true
    dynamodb_table = "auth-api-terraform-locks"
  }
}
