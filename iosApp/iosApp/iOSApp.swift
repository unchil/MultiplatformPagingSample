import SwiftUI

import GoogleMaps

@main
struct iOSApp: App {
    

    init(){
        
        // infoDictionary가 nil이거나 유효하지 않으면 런타임 에러 발생 (개발 단계에서 문제 파악)
        guard let infoDict = Bundle.main.infoDictionary else {
            fatalError("Info.plist not found or cannot be read.")
        }
    
        // GOOGLEMAP_API_KEY 가져오기
        guard let googleMapAPIKey = infoDict["GOOGLEMAP_API_KEY"] as? String else {
            fatalError("GOOGLEMAP_API_KEY not found in Info.plist. Make sure it's defined in .xcconfig and injected into Info.plist.")
        }

        GMSServices.provideAPIKey(googleMapAPIKey)
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
