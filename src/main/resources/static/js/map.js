async function initSystem() {
    try {
        // 1. 뷰어 초기화 (Nginx를 통해 지형 로드)
        viewer = new Cesium.Viewer("map", {
            terrainProvider: await Cesium.CesiumTerrainProvider.fromUrl(`/cmworld/map/demN`),
            animation: false,
            timeline: false,
            infoBox: false,
            selectionIndicator: false,
            navigationHelpButton: false,
            baseLayerPicker: false
        });

        viewer.shadows = false;

        // 2. 모델 로드 (추후 이 부분을 DB API 호출로 바꿀 예정입니다)
        // 현재는 Nginx 정적 경로에 파일이 있다고 가정합니다.
        const assets = [
            {
                name: 'hapgang',
                // 바탕화면 폴더 내의 상대 경로가 '3ds테스트/hapgang/...' 일 경우
                czml: '/local-data/hapgang/model_msl_af.czml'
            },
            {
                name: 'dam',
                czml: '/local-data/dam/model_eli_af.czml'
            }
        ];

        document.getElementById('loading-status').innerText = "데이터 분석 중...";

        for (const asset of assets) {
            const dataSource = await viewer.dataSources.add(
                Cesium.CzmlDataSource.load(asset.czml)
            );
            globalDataSources.push(dataSource);
        }

        const toggleBtn = document.getElementById('layerToggle');
        toggleBtn.disabled = false;
        document.getElementById('layerLabel').innerText = "통합 레이어 표시 중";
        document.getElementById('loading-status').innerText = "✅ 로드 완료";

        toggleBtn.addEventListener('change', (e) => {
            globalDataSources.forEach(ds => ds.show = e.target.checked);
        });

        zoomToLayer(0);

    } catch (error) {
        console.error("오류 발생:", error);
        document.getElementById('loading-status').innerText = "❌ 로드 실패";
    }
}

function zoomToLayer(index) {
    if (!viewer || !globalDataSources[index]) return;
    const source = globalDataSources[index];
    viewer.flyTo(source, { duration: 2.0 });
}