self.addEventListener("install", (event) => {
    event.waitUntil(
        caches.open("v1").then((cache) => {
            return cache.addAll([
                "/",               // 메인 경로
                "/style.css",      // CSS
                "/img/icon-192.png", // 아이콘
                "/manifest.json"   // 매니페스트
            ]);
        })
    );
});

self.addEventListener("fetch", (event) => {
    event.respondWith(
        caches.match(event.request).then((response) => {
            return response || fetch(event.request);
        })
    );
});
