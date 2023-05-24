param (
    [string]$tag = "latest"
)

If ( $tag ){ docker build -t apigateway_commons:$tag . }
Else { docker build -t apigateway_commons:latest . }
