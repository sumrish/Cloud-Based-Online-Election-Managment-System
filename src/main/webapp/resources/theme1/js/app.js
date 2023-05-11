'use strict';
angular.module('example366', ['ngAnimate', 'ngTouch'])
  .controller('MainCtrl', function ($scope) {
    // Set of Photos
    $scope.photos = [
        {src: 'https://free4kwallpapers.com/wallpaper/travel-world/pakistan-flag/z6yq/800/480/download', desc: 'Image 01'},
        {src: 'http://www.thenewstribe.com/wp-content/uploads/2014/09/ECP3.jpg', desc: 'Image 02'},
        {src: 'http://www.thenewstribe.com/wp-content/uploads/2015/05/elec.jpg', desc: 'Image 03'},
        {src: 'http://i.dawn.com/large/2015/06/558b7dda3c9f6.jpg', desc: 'Image 04'},
        {src: 'http://i.dawn.com/large/2014/12/547d065923dea.jpg', desc: 'Image 05'},
        {src: 'http://i.dawn.com/primary/2015/08/55e290ba2b218.jpg', desc: 'Image 06'}
    ];
    // initial image index
    $scope._Index = 0;
    // if a current image is the same as requested image
    $scope.isActive = function (index) {
        return $scope._Index === index;
    };
    // show prev image
    $scope.showPrev = function () {
        $scope._Index = ($scope._Index > 0) ? --$scope._Index : $scope.photos.length - 1;
    };
    // show next image
    $scope.showNext = function () {
        $scope._Index = ($scope._Index < $scope.photos.length - 1) ? ++$scope._Index : 0;
    };
    // show a certain image
    $scope.showPhoto = function (index) {
        $scope._Index = index;
    };
});