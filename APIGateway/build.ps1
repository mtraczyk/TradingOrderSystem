param (
    [string]$tag = "latest"
)

If ( $tag ){ docker build -t apigateway_logicgateway:$tag . }
Else { docker build -t apigateway_logicgateway:latest . }
