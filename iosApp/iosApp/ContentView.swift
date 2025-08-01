import UIKit
import SwiftUI
import ComposeApp
import shared
import GoogleMaps

struct GoogleMapView: UIViewRepresentable {

    func makeUIView(context: Context) -> GMSMapView {
        let options = GMSMapViewOptions()
        options.camera = GMSCameraPosition.camera(withLatitude: 37.5665, longitude: 126.9780, zoom: 10.0)
        let mapView = GMSMapView(options: options)
        return mapView
    }

    func updateUIView(_ uiView: GMSMapView, context: Context) {

    }
}

struct MapViewContainer: UIViewRepresentable {

    @State private var cameraPosition = GMSCameraPosition.camera(withLatitude: 37.5665, longitude: 126.9780, zoom: 12.0)
    @State private var markers: [GMSMarker] = []

    func makeUIView(context: Context) -> GMSMapView {
        let options = GMSMapViewOptions()
        options.camera = cameraPosition
        let mapView = GMSMapView(options: options)
        return mapView
    }

    func updateUIView(_ uiView: GMSMapView, context: Context) {
        uiView.camera = cameraPosition // 카메라 위치 업데이트
        uiView.clear() // 기존 마커 지우기
        markers.forEach { marker in
            marker.map = uiView // 새로운 마커 추가
        }
    }

    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    class Coordinator: NSObject, GMSMapViewDelegate {
        var parent: MapViewContainer

        init(_ parent: MapViewContainer) {
            self.parent = parent
        }

        // GMSMapViewDelegate 메서드 구현 (예: 마커 탭)
        func mapView(_ mapView: GMSMapView, didTap marker: GMSMarker) -> Bool {
            print("Marker tapped: \(marker.title ?? "")")
            return false // true를 반환하면 탭 이벤트가 소비되어 info window가 표시되지 않을 수 있음
        }

        // 기타 델리게이트 메서드 구현


    }
}

struct ComposeView: UIViewControllerRepresentable {


    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(

            mapUIViewController: { () -> UIViewController in
                return UIHostingController(rootView: MapViewContainer())
            }

        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}


struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



