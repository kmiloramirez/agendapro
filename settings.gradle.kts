rootProject.name = "agendapro_products"

include(":domain")
project(":domain").projectDir = file("./domain")
include(":application")
project(":application").projectDir = file("./application")
include(":rest-api")
project(":rest-api").projectDir = file("./infrastructure/entry-points/rest-api")
include(":data-base")
project(":data-base").projectDir = file("./infrastructure/driven-adapters/data-base")
include(":events")
project(":events").projectDir = file("./infrastructure/internal-envents/events")
include(":local-memory")
project(":local-memory").projectDir = file("./infrastructure/driven-adapters/local-memory")


