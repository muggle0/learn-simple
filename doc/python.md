## python 语法
https://baijiahao.baidu.com/s?id=1817133615057539187&wfr=spider&for=pc

```python
def method():
    if condition:
```

Python 使用缩进来定义代码块，不需要花括号
Python 不需要分号结尾
Python 不需要显式声明类（除非必要）

### 变量

```python
name = "John"    # 动态类型，无需声明
age = 25
list = []        # 列表声明更简单
```

#### 面向对象编程

```python
class Person:
    def __init__(self, name):
        self.name = name
    
    def say_hello(self):
        print(f"Hello, {self.name}"
```

Python 使用 self 代替 Java 的 this
Python 不需要声明访问修饰符（public/private）
Python 使用 __init__ 作为构造函数
Python 的方法命名通常使用下划线命名法

### 函数声明

```python
# 基本函数声明
def add(a, b):
    return a + b

# 静态方法
@staticmethod
def static_method():
    print("Static method")

# 可变参数
def print_all(*args):
    for arg in args:
        print(arg)

# 带默认参数的函数
def greet(name, greeting="Hello"):
    print(f"{greeting}, {name}")

# 关键字参数
def person_info(**kwargs):
    for key, value in kwargs.items():
        print(f"{key}: {value}")
```

### 继承与多态

```python

class Animal:
    def __init__(self, name):
        self.name = name
    
    def make_sound(self):
        print("Some sound")

class Dog(Animal):
    def __init__(self, name):
        super().__init__(name)
    
    def make_sound(self):
        print("Woof!")

# 多重继承示例
class Pet:
    def play(self):
        print("Playing")

class DomesticDog(Dog, Pet):
    pass
    
```

### 集合操作

```python
list = ["a", "b", "c"]
dict = {"key": 1}

# 列表操作
list.append("d")
list[0]  # 访问元素
list[1:3]  # 切片操作

# 字典操作
dict["new_key"] = 2
```

### 特殊方法与装饰器

魔术方法
```python
class Person:
    def __init__(self, name):
        self.name = name
    
    def __str__(self):
        return f"Person: {self.name}"
    
    def __eq__(self, other):
        if isinstance(other, Person):
            return self.name == other.name
        return False
```

属性装饰器
```python
class Person:
    def __init__(self):
        self._name = None
    
    @property
    def name(self):
        return self._name
    
    @name.setter
    def name(self, value):
        self._name = value
```

### 异常处理

```python
try:
    raise Exception("Error")
except Exception as e:
    print(f"Caught: {str(e)}")
finally:
    # 清理代码
```

### Python特有特性

```python 
## 切片操作
list = [1, 2, 3, 4, 5]
print(list[1:3])    # 输出 [2, 3]
```

```python
a, b = 1, 2
x, y = y, x    # 交换变量值
```

### 学习建议
重点关注 Python 的缩进规则
习惯不使用类型声明
多使用 Python 的内置函数和特性
学习 Python 的列表推导式和切片操作
使用 f-string 进行字符串格式化
熟悉 Python 的命名规范（下划线命名法）
理解 Python 的继承机制，特别是 super() 的使用
掌握 Python 的特殊方法（魔术方法）
学习使用装饰器
了解 Python 的异常处理机制
