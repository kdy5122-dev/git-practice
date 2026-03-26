/**
 * CM-World 지도 엔진 모듈
 */
let viewer;
let globalDataSources = [];

// 1. 초기화 함수
async function initSystem(config) {
    try {
        console.log("지도 엔진 초기화 시작...");

        // 서버에서 전달받은 토큰 설정
        Cesium.Ion.defaultAccessToken = config.token;

        // 2. 뷰어 초기화
        viewer = new Cesium.Viewer("map", {
            terrainProvider: await Cesium.CesiumTerrainProvider.fromUrl(config.terrainUrl),
            animation: false,
            timeline: false,
            infoBox: false,
            selectionIndicator: false,
            navigationHelpButton: false,
            baseLayerPicker: false
        });

        viewer.shadows = false;

        // 3. 모델 로드 (데이터셋 정의)
        const assets = [
            { name: 'hapgang', czml: '/local-data/hapgang/model_msl_af.czml' },
            { name: 'dam',     czml: '/local-data/dam/model_eli_af.czml' }
        ];

        // UI 업데이트용 헬퍼 함수 (에러 방지용)
        updateStatus("데이터 분석 중...");

        for (const asset of assets) {
            const dataSource = await viewer.dataSources.add(
                Cesium.CzmlDataSource.load(asset.czml)
            );
            globalDataSources.push(dataSource);
        }

        // 레이어 토글 이벤트 연결
        const toggleBtn = document.getElementById('layerToggle');
        if (toggleBtn) {
            toggleBtn.disabled = false;
            toggleBtn.addEventListener('change', (e) => {
                globalDataSources.forEach(ds => ds.show = e.target.checked);
            });
            document.getElementById('layerLabel').innerText = "통합 레이어 표시 중";
        }

        updateStatus("✅ 로드 완료");
        zoomToLayer(0); // 초기 위치 이동

    } catch (error) {
        console.error("오류 발생:", error);
        updateStatus("❌ 로드 실패");
    }
}

// 4. 레이어 이동 함수 (전역 범위 유지)
function zoomToLayer(index) {
    if (!viewer || !globalDataSources[index]) {
        console.warn("데이터가 준비되지 않았습니다.");
        return;
    }
    viewer.flyTo(globalDataSources[index], { duration: 2.0 });
}

// 5. UI 상태 업데이트 안전 함수
function updateStatus(msg) {
    const statusEl = document.getElementById('loading-status');
    if (statusEl) statusEl.innerText = msg;
}