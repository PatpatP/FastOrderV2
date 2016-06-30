<jsp:directive.page import="java.util.List" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="https://maps.googleapis.com/maps/api/js"
    	type="text/javascript"></script>

<div ng-app="fastOrder">
<script>
			
			var locations = ${listShopAddress };
       		var geocoder;
       		var map;
       		var bounds = new google.maps.LatLngBounds();
	
       		function initialize() {
       		  map = new google.maps.Map(
       		    document.getElementById("map"), {
       		      center: new google.maps.LatLng(37.4419, -122.1419),
       		      zoom: 5,
       		      mapTypeId: google.maps.MapTypeId.ROADMAP
       		    });
       		  geocoder = new google.maps.Geocoder();

       		  for (i = 0; i < locations.length; i++) {


       		    geocodeAddress(locations, i);
       		  }
       		}
       		google.maps.event.addDomListener(window, "load", initialize);

       		function geocodeAddress(locations, i) {
       		  var title = locations[i][0];
       		  var address = locations[i][1];
       		  var url = locations[i][2];
       		  geocoder.geocode({
       		      'address': locations[i][1]
       		    },

       		    function(results, status) {
       		      if (status == google.maps.GeocoderStatus.OK) {
       			var marker = new google.maps.Marker({
       			  icon: 'http://maps.google.com/mapfiles/ms/icons/red.png',
       			  map: map,
       			  position: results[0].geometry.location,
       			  title: title,
       			  animation: google.maps.Animation.DROP,
       			  address: address,
       			  url: url
       			})
       			infoWindow(marker, map, title, address, url);
       			bounds.extend(marker.getPosition());
       			map.fitBounds(bounds);
       		      } else {
       			alert("geocode of " + address + " failed:" + status);
       		      }
       		    });
       		}

       		function infoWindow(marker, map, title, address, url) {
       		  google.maps.event.addListener(marker, 'click', function() {
       		    var html = "<div><h3>" + title + "</h3><p>" + address + "<br></div><a href='" + url + "'>Passer une commande</a></p></div>";
       		    iw = new google.maps.InfoWindow({
       		      content: html,
       		      maxWidth: 350
       		    });
       		    iw.open(map, marker);
       		  });
       		}

       		function createMarker(results) {
       		  var marker = new google.maps.Marker({
       		    icon: 'http://maps.google.com/mapfiles/ms/icons/red.png',
       		    map: map,
       		    position: results[0].geometry.location,
       		    title: title,
       		    animation: google.maps.Animation.DROP,
       		    address: address,
       		    url: url
       		  })
       		  bounds.extend(marker.getPosition());
       		  map.fitBounds(bounds);
       		  infoWindow(marker, map, title, address, url);
       		  return marker;
       		}
</script>
</div>