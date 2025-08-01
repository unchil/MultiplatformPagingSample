//
// Created by 여운칠 on 2025. 7. 28..
//

import Foundation
import GoogleMaps
import shared

class GMSMarkerController: ObservableObject {

    @Published var currentMarker: GMSMarker?
    @Published var markers: [GMSMarker] = []

    func setMarkers(results:[SharedMarker], completionHandler: @escaping (Bool) -> Void ){
        self.markers.removeAll()
        results.forEach { item in
            let location = CLLocationCoordinate2D(latitude: item.latitude , longitude: item.longitude )
            let marker = GMSMarker(position:location)
            marker.snippet = item.snippet
            marker.title = item.title
         //   marker.userData = GMSMarkerUserData(id: item.writetime, snapshotF//ileName: item.snapshotFileName!)
            self.markers.append(marker)
        }
        completionHandler(true)
    }




    func setCurrentMarker(location:CLLocation){
        self.currentMarker = GMSMarker(position: location.coordinate)
    }
}

struct GMSMarkerUserData {
    var id: Double = 0
    var snapshotFileName: String = ""
}
