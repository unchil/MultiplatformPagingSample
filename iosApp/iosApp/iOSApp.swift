import SwiftUI

import GoogleMaps

@main
struct iOSApp: App {
    init(){
        GMSServices.provideAPIKey("GoogleMap Api Key")
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
