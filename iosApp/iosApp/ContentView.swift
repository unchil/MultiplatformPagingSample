import UIKit
import SwiftUI
import ComposeApp

import GoogleMaps

struct GoogleMapView: UIViewRepresentable {

    func makeUIView(context: Context) -> GMSMapView {
        let options = GMSMapViewOptions()
        options.camera = GMSCameraPosition.camera(withLatitude: 37.5665, longitude: 126.9780, zoom: 10.0)
        let mapView = GMSMapView(options: options)
        return mapView
    }

    func updateUIView(_ uiView: GMSMapView, context: Context) {}

}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(
            mapUIViewController: { () -> UIViewController in
                return UIHostingController(rootView: GoogleMapView())
            }
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

/*
struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
*/

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



