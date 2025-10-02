# Calculator App - MVVM Architecture

## 專案架構概覽

本專案採用 **MVVM (Model-View-ViewModel)** 架構模式，遵循 Android 開發最佳實踐。

```
app/src/main/java/com/example/calculate/
│
├── domain/                          # Domain Layer (業務邏輯層)
│   └── CalculatorEngine.kt         # 純計算邏輯，無 Android 依賴
│
├── presentation/                    # Presentation Layer (展示層)
│   ├── calculator/                  # Calculator 功能模組
│   │   ├── BaseCalculatorFragment.kt   # Fragment 基類 (View)
│   │   ├── Calc1Fragment.kt            # 淺色主題計算器 (View)
│   │   ├── Calc2Fragment.kt            # 深色主題計算器 (View)
│   │   ├── CalculatorViewModel.kt      # ViewModel (狀態管理)
│   │   └── CalculatorUiState.kt        # UI 狀態數據類
│   │
│   └── main/                        # Main 功能模組
│       └── MainActivity.kt          # 主 Activity (View)
│
└── ui/theme/                        # UI Theme (Compose - 未使用)
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

## MVVM 架構說明

### 📊 架構層級

```
┌─────────────────────────────────────────────────┐
│                    View Layer                    │
│  (Fragment/Activity - 負責顯示 UI 和用戶交互)    │
│                                                  │
│  • Calc1Fragment.kt                              │
│  • Calc2Fragment.kt                              │
│  • MainActivity.kt                               │
└─────────────────┬───────────────────────────────┘
                  │ observes (LiveData)
                  │
┌─────────────────▼───────────────────────────────┐
│                 ViewModel Layer                  │
│         (管理 UI 狀態和業務邏輯協調)              │
│                                                  │
│  • CalculatorViewModel.kt                        │
│  • CalculatorUiState.kt                          │
└─────────────────┬───────────────────────────────┘
                  │ uses
                  │
┌─────────────────▼───────────────────────────────┐
│                 Domain Layer                     │
│          (純業務邏輯，無框架依賴)                │
│                                                  │
│  • CalculatorEngine.kt                           │
└─────────────────────────────────────────────────┘
```

### 🎯 各層職責

#### **Domain Layer (領域層)**
- **職責**: 純業務邏輯
- **特點**:
  - 不依賴 Android 框架
  - 易於單元測試
  - 可重用於其他平台
- **檔案**: `CalculatorEngine.kt`

#### **ViewModel Layer (視圖模型層)**
- **職責**:
  - 管理 UI 狀態
  - 處理用戶交互邏輯
  - 協調 Domain 層與 View 層
- **特點**:
  - 在配置變更（如螢幕旋轉）時保留狀態
  - 使用 LiveData 提供可觀察的數據
- **檔案**:
  - `CalculatorViewModel.kt` - 狀態管理
  - `CalculatorUiState.kt` - UI 狀態定義

#### **View Layer (視圖層)**
- **職責**:
  - 渲染 UI
  - 處理用戶輸入
  - 觀察 ViewModel 變化並更新 UI
- **特點**:
  - 輕量級，最少邏輯
  - 透過 ViewModel 與業務邏輯解耦
- **檔案**:
  - `Calc1Fragment.kt` - 淺色主題
  - `Calc2Fragment.kt` - 深色主題
  - `BaseCalculatorFragment.kt` - 共享邏輯
  - `MainActivity.kt` - Fragment 容器

## 🔄 數據流向

```
User Input → Fragment → ViewModel → Domain → ViewModel → Fragment → UI Update
    ↓           ↓           ↓           ↓         ↓          ↓          ↓
  點擊按鈕   觸發事件   調用方法   執行計算   更新狀態   觀察變化   更新顯示
```

### 範例流程：用戶點擊 "5"

1. **View**: `Calc1Fragment` 捕獲按鈕點擊
2. **View**: 調用 `viewModel.onNumberClick(5)`
3. **ViewModel**: `CalculatorViewModel` 調用 `engine.appendDigit(5)`
4. **Domain**: `CalculatorEngine` 更新內部狀態
5. **ViewModel**: 更新 `_uiState.value` (LiveData)
6. **View**: Fragment 觀察到 `uiState` 變化
7. **View**: 更新 `tvDisplay.text`

## ✨ MVVM 優勢

### 1. **關注點分離**
- UI 邏輯與業務邏輯完全分離
- 每個類別都有明確的單一職責

### 2. **易於測試**
```kotlin
// Domain 層可以獨立測試
@Test
fun testCalculation() {
    val engine = CalculatorEngine()
    engine.appendDigit(5)
    engine.setOperation(Operation.ADD)
    engine.appendDigit(3)
    engine.calculate()
    assertEquals("8", engine.getDisplayValue())
}

// ViewModel 層也可以測試
@Test
fun testViewModel() {
    val viewModel = CalculatorViewModel()
    viewModel.onNumberClick(5)
    assertEquals("5", viewModel.uiState.value?.displayValue)
}
```

### 3. **配置變更處理**
- ViewModel 在螢幕旋轉時自動保留
- 不需要手動保存/恢復狀態

### 4. **可維護性**
- 清晰的代碼組織
- 易於添加新功能
- 減少耦合

### 5. **可擴展性**
- 添加新功能只需修改對應層
- 例如：添加歷史記錄功能
  - Domain: 添加歷史管理邏輯
  - ViewModel: 添加歷史狀態
  - View: 添加歷史顯示 UI

## 🛠️ 使用的技術

- **Kotlin** - 程式語言
- **Android Jetpack**:
  - ViewModel - 狀態管理
  - LiveData - 響應式數據
  - Fragment - UI 組件
- **ConstraintLayout** - 現代化佈局
- **GridLayout** - 按鈕網格排列

## 📦 依賴管理

```kotlin
// ViewModel and LiveData
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
implementation("androidx.fragment:fragment-ktx:1.8.5")
```

## 🎨 設計模式

1. **MVVM** - 整體架構
2. **Observer Pattern** - LiveData 觀察者模式
3. **Template Method** - BaseCalculatorFragment
4. **Single Responsibility** - 每個類別單一職責
5. **Dependency Inversion** - ViewModel 不直接依賴 View

## 🚀 未來改進方向

1. **添加 Repository 層** (如需要資料持久化)
   ```
   ViewModel → Repository → Local/Remote DataSource
   ```

2. **使用 StateFlow 取代 LiveData** (更現代的方式)
   ```kotlin
   private val _uiState = MutableStateFlow(CalculatorUiState())
   val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()
   ```

3. **依賴注入** (使用 Hilt/Koin)
   ```kotlin
   @HiltViewModel
   class CalculatorViewModel @Inject constructor(
       private val engine: CalculatorEngine
   ) : ViewModel()
   ```

4. **單元測試覆蓋率**
   - CalculatorEngine 測試
   - CalculatorViewModel 測試
   - UI 測試 (Espresso)

## 📝 總結

本專案完整展示了 Android MVVM 架構的實現，包括：
- ✅ 清晰的分層架構
- ✅ 可測試的業務邏輯
- ✅ 響應式 UI 更新
- ✅ 配置變更處理
- ✅ 符合 Android 最佳實踐

這是一個可以作為參考的 Android 專案範本。
