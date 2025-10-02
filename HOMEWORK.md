# Android 計算機作業報告

**學生姓名：** [學生姓名]
**學號：** [學號]
**繳交日期：** 2025-10-02
**專案名稱：** Calculate - Android Calculator App

---

## 📋 作業完成情況

### ✅ 基本要求

#### 1. 調整適合畫面

**完成內容：**
- 使用現代化的 `ConstraintLayout` 和 `GridLayout` 重新設計計算機界面
- 實現響應式佈局，按鈕使用 `layout_weight` 均分空間
- 計算器顯示區域使用大字體 (36sp) 和適當的內距 (24dp)
- 按鈕網格採用 4x5 排列，視覺平衡且易於操作

**技術細節：**
```xml
<!-- 顯示區域 -->
<TextView
    android:id="@+id/tvDisplay"
    android:textSize="36sp"
    android:padding="24dp"
    android:gravity="end|center_vertical" />

<!-- 按鈕網格 -->
<GridLayout
    android:columnCount="4"
    android:rowCount="5"
    android:layout_width="0dp"
    android:layout_height="0dp" />
```

**畫面截圖：**
- Light Theme (淺色主題)：使用清新的藍綠配色
- Dark Theme (深色主題)：使用專業的暗色系設計

---

#### 2. 寫入計算機元件的事件

**完成內容：**

實現了完整的計算機功能，包括：

**數字輸入 (0-9)：**
```kotlin
numberButtons.forEach { (id, number) ->
    view.findViewById<Button>(id).setOnClickListener {
        viewModel.onNumberClick(number)
    }
}
```

**運算符 (+, -, ×, ÷)：**
```kotlin
view.findViewById<Button>(R.id.btnAdd).setOnClickListener {
    viewModel.onOperationClick(CalculatorEngine.Operation.ADD)
}
// ... 其他運算符
```

**計算與顯示：**
```kotlin
view.findViewById<Button>(R.id.btnEquals).setOnClickListener {
    try {
        viewModel.onEqualsClick()
    } catch (e: ArithmeticException) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    }
}
```

**功能測試範例：**
- 5 + 3 = 8 ✅
- 12 × 4 = 48 ✅
- 100 ÷ 5 = 20 ✅
- 15 - 7 = 8 ✅
- 10 ÷ 0 → 顯示錯誤訊息 ✅

---

#### 3. 執行畫面與學習心得

**執行畫面說明：**

```
主畫面結構：
┌─────────────────────────────────┐
│ [Light Theme] [Dark Theme]      │ ← 主題切換按鈕
├─────────────────────────────────┤
│              0                   │ ← 顯示區域
│  [C]  [AC]  [÷]  [×]           │
│  [7]  [8]   [9]  [-]           │
│  [4]  [5]   [6]  [+]           │ ← 計算按鈕
│  [1]  [2]   [3]  [=]           │
│  [.]  [0]        [=]           │
└─────────────────────────────────┘
```

**學習心得：**

1. **Fragment 生命週期管理**
   - 學會使用 `onViewCreated()` 初始化 UI 元件
   - 理解 Fragment 的創建和銷毀流程
   - 掌握 FragmentManager 的 replace() 操作

2. **事件處理機制**
   - 使用 Kotlin 的 `forEach` 簡化重複代碼
   - 學會使用 Lambda 表達式處理點擊事件
   - 理解 Android 的事件驅動模型

3. **UI 佈局設計**
   - GridLayout 比 TableLayout 更靈活
   - ConstraintLayout 提供更精確的控制
   - 使用 `layout_weight` 實現響應式設計

4. **錯誤處理**
   - 實現除以零的異常捕獲
   - 使用 Toast 顯示友好的錯誤訊息
   - 學會防禦性編程思維

---

### ✅ 加分題

#### 1. 將 Calc1 和 Calc2 改成獨立文件

**完成內容：**

原本所有類別都在 `MainActivity.kt` 中，現已重構為獨立文件：

**文件結構：**
```
presentation/calculator/
├── Calc1Fragment.kt      # 淺色主題計算器
├── Calc2Fragment.kt      # 深色主題計算器
├── BaseCalculatorFragment.kt
├── CalculatorViewModel.kt
└── CalculatorUiState.kt
```

**Calc1Fragment.kt：**
```kotlin
package com.example.calculate.presentation.calculator

import com.example.calculate.R

/**
 * Calculator Fragment with light theme
 * Displays a calculator interface with modern blue-green color scheme
 */
class Calc1Fragment : BaseCalculatorFragment(R.layout.calc1)
```

**Calc2Fragment.kt：**
```kotlin
package com.example.calculate.presentation.calculator

import com.example.calculate.R

/**
 * Calculator Fragment with dark theme
 * Displays a calculator interface with professional dark color scheme
 */
class Calc2Fragment : BaseCalculatorFragment(R.layout.calc2)
```

**優勢：**
- 程式碼組織更清晰
- 符合單一職責原則
- 易於維護和擴展
- 遵循 Android 開發慣例

---

#### 2. 設計不同 View

**完成內容：**

設計了兩種不同的視覺主題：

**Light Theme (calc1.xml)：**
- 背景色：`#E0E5EC` (淺灰藍)
- 數字按鈕：`#95A5A6` (灰色)
- 運算符：`#4ECDC4` (藍綠色)
- 清除按鈕：`#FF6B6B` (紅色)
- 等於按鈕：`#44BD32` (綠色)

**Dark Theme (calc2.xml)：**
- 背景色：`#1E1E1E` (深灰)
- 顯示區域：`#2D2D2D` (更深的灰)
- 數字按鈕：`#505050` (中灰)
- 運算符：`#FFA000` (橘色)
- 清除按鈕：`#D32F2F` (深紅)
- 等於按鈕：`#388E3C` (深綠)

**設計特點：**
- 兩個主題有明顯的視覺差異
- 色彩搭配符合 Material Design 規範
- 提供良好的視覺對比度
- 使用者可根據喜好切換

**對比展示：**
```
Light Theme          Dark Theme
┌──────────┐        ┌──────────┐
│   淺色背景 │        │   深色背景 │
│  藍綠按鈕  │        │  橘色按鈕  │
│  清新明亮  │        │  專業沉穩  │
└──────────┘        └──────────┘
```

---

#### 3. 加入清除按鍵

**完成內容：**

新增了兩種清除功能：

**C (Clear Entry) 按鈕：**
- 功能：清除當前輸入
- 位置：左上角第一個按鈕
- 使用情境：輸入錯誤時快速清除當前數字

```kotlin
view.findViewById<Button>(R.id.btnClear).setOnClickListener {
    viewModel.onClearEntryClick()
}
```

**AC (All Clear) 按鈕：**
- 功能：完全重置計算器
- 位置：左上角第二個按鈕
- 使用情境：開始新的計算

```kotlin
view.findViewById<Button>(R.id.btnAllClear).setOnClickListener {
    viewModel.onAllClearClick()
}
```

**實作邏輯：**
```kotlin
// CalculatorEngine.kt
fun clearEntry() {
    currentValue = 0.0
    isNewNumber = true
    hasDecimal = false
}

fun clear() {
    currentValue = 0.0
    operand = 0.0
    currentOperation = null
    isNewNumber = true
    hasDecimal = false
}
```

**使用範例：**
- 輸入 5 + → 按 C → 顯示 0 (保留運算符)
- 輸入 5 + 3 → 按 AC → 顯示 0 (完全重置)

---

## 🚀 額外實現：MVVM 架構重構

### 為什麼要重構？

雖然作業沒有要求，但為了達到軟體工程的最佳實踐，我將整個專案重構為 **MVVM (Model-View-ViewModel)** 架構。

### 架構說明

**專案結構：**
```
app/src/main/java/com/example/calculate/
│
├── domain/                          # 業務邏輯層
│   └── CalculatorEngine.kt         # 純計算邏輯
│
├── presentation/                    # 展示層
│   ├── calculator/                  # 計算器模組
│   │   ├── BaseCalculatorFragment.kt
│   │   ├── Calc1Fragment.kt
│   │   ├── Calc2Fragment.kt
│   │   ├── CalculatorViewModel.kt   # 狀態管理
│   │   └── CalculatorUiState.kt     # UI 狀態
│   │
│   └── main/
│       └── MainActivity.kt          # 主 Activity
```

**架構層級：**
```
View (Fragment)
    ↓ 觸發事件
ViewModel
    ↓ 調用業務邏輯
Domain (CalculatorEngine)
    ↓ 返回結果
ViewModel
    ↓ 更新 LiveData
View (自動更新 UI)
```

### MVVM 的優勢

1. **關注點分離**
   - UI 邏輯與業務邏輯完全分離
   - 每個類別都有明確的職責

2. **易於測試**
   ```kotlin
   // 可以獨立測試 CalculatorEngine
   @Test
   fun testAddition() {
       val engine = CalculatorEngine()
       engine.appendDigit(5)
       engine.setOperation(Operation.ADD)
       engine.appendDigit(3)
       engine.calculate()
       assertEquals("8", engine.getDisplayValue())
   }
   ```

3. **配置變更處理**
   - ViewModel 在螢幕旋轉時自動保留狀態
   - 不需要手動保存/恢復計算狀態

4. **可維護性**
   - 代碼結構清晰
   - 易於添加新功能
   - 遵循 SOLID 原則

### 核心實現

**ViewModel 狀態管理：**
```kotlin
class CalculatorViewModel : ViewModel() {
    private val engine = CalculatorEngine()

    private val _uiState = MutableLiveData(CalculatorUiState())
    val uiState: LiveData<CalculatorUiState> = _uiState

    fun onNumberClick(digit: Int) {
        engine.appendDigit(digit)
        updateDisplay()
    }

    private fun updateDisplay() {
        _uiState.value = _uiState.value?.copy(
            displayValue = engine.getDisplayValue()
        )
    }
}
```

**Fragment 觀察狀態：**
```kotlin
abstract class BaseCalculatorFragment : Fragment() {
    protected val viewModel: CalculatorViewModel by viewModels()

    private fun observeViewModel() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            tvDisplay.text = state.displayValue
            state.errorMessage?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
```

---

## 📊 技術亮點總結

### 使用的技術

1. **Android Jetpack**
   - ViewModel (狀態管理)
   - LiveData (響應式數據)
   - Fragment (模組化 UI)

2. **Kotlin 特性**
   - Lambda 表達式
   - 擴展函數
   - Data Class
   - Sealed Class (Operation enum)

3. **現代佈局**
   - ConstraintLayout
   - GridLayout
   - Material Design 配色

4. **設計模式**
   - MVVM 架構
   - Observer Pattern (LiveData)
   - Template Method (BaseCalculatorFragment)

### 代碼品質

- **可讀性**: 清晰的命名和註釋
- **可維護性**: 模組化設計
- **可擴展性**: 易於添加新功能
- **可測試性**: 業務邏輯與 UI 分離

---

## 🎓 學習收穫

### 技術層面

1. **Android 開發基礎**
   - 掌握 Fragment 生命週期
   - 理解 Activity 與 Fragment 的關係
   - 學會使用 FragmentManager

2. **進階架構**
   - 理解 MVVM 架構原理
   - 學會使用 ViewModel 和 LiveData
   - 掌握響應式編程思維

3. **UI/UX 設計**
   - 學會使用現代佈局系統
   - 理解 Material Design 原則
   - 掌握色彩搭配技巧

4. **程式設計思維**
   - 單一職責原則
   - 開放封閉原則
   - 依賴反轉原則

### 軟體工程實踐

1. **代碼組織**
   - 按功能分層（domain/presentation）
   - 一個文件一個類別
   - 清晰的目錄結構

2. **最佳實踐**
   - 使用 NoActionBar 主題自訂 UI
   - 實現錯誤處理機制
   - 添加適當的註釋和文檔

3. **版本控制**
   - 理解專案重構流程
   - 學會漸進式改進

---

## 💡 心得反思

### 遇到的挑戰

1. **UI 佈局問題**
   - **問題**: ActionBar 遮住切換按鈕
   - **解決**: 改用 NoActionBar 主題
   - **學習**: 理解 Android 主題系統

2. **按鈕文字截斷**
   - **問題**: 固定高度壓縮文字
   - **解決**: 使用 wrap_content 和適當 padding
   - **學習**: 掌握佈局計算原理

3. **架構重構**
   - **問題**: 從簡單結構到 MVVM 的轉換
   - **解決**: 逐步重構，保持功能正常
   - **學習**: 大型重構的策略

### 個人成長

通過這次作業，我不僅完成了基本要求和加分題，還主動將專案重構為專業級的 MVVM 架構。這個過程讓我深刻理解了：

1. **好的架構很重要** - MVVM 讓代碼更清晰、更易維護
2. **細節決定成敗** - UI 的每個細節都需要仔細調整
3. **持續學習** - Android 開發涉及很多技術，需要不斷學習

### 未來改進方向

如果有更多時間，我會繼續改進：

1. **添加單元測試** - 測試 CalculatorEngine 和 ViewModel
2. **實現歷史記錄** - 記錄計算歷史
3. **添加更多功能** - 科學計算器模式
4. **優化動畫** - 添加切換主題的過渡動畫

---

## 📚 參考資料

1. [Android Developer Documentation](https://developer.android.com/)
2. [Android Architecture Components](https://developer.android.com/topic/architecture)
3. [Material Design Guidelines](https://material.io/design)
4. [Kotlin Official Documentation](https://kotlinlang.org/docs/home.html)

---

## 📝 結語

這次作業讓我從一個簡單的計算機應用，學習到完整的 Android 開發流程和軟體工程最佳實踐。不僅完成了所有要求，還額外實現了 MVVM 架構，為未來的 Android 開發打下了堅實的基礎。

感謝老師提供這個寶貴的學習機會！

---

**專案資訊：**
- **GitHub**: (如有上傳可填寫)
- **建置工具**: Gradle 8.x
- **開發環境**: Android Studio
- **測試設備**: Android Emulator API 36
- **最低支援版本**: Android 7.0 (API 24)
- **目標版本**: Android 14 (API 36)
