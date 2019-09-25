import 'package:NeoStencil/custom_loader_painter.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

const double _kLinearProgressIndicatorHeight = 6.0;
const double _kMinCircularProgressIndicatorSize = 36.0;
String semanticsValue;
String semanticsLabel;

class CustomLoader extends StatefulWidget{

  final double value;





  const CustomLoader({Key key, this.value}) : super(key: key);



  @override
  _CustomLoaderState createState() {
    return _CustomLoaderState();
  }




  Widget _buildSemanticsWrapper({
    @required BuildContext context,
    @required Widget child,
  }) {
    String expandedSemanticsValue = semanticsValue;
    if (value != null) {
      expandedSemanticsValue ??= '${(value * 100).round()}%';
    }
    return Semantics(
      label: semanticsLabel,
      value: expandedSemanticsValue,
      child: child,
    );
  }
}

class _CustomLoaderState extends State<CustomLoader> with SingleTickerProviderStateMixin {
  AnimationController _controller;

  @override
  void initState() {
    super.initState();
    _controller = AnimationController(
      duration: const Duration(seconds: 5),
      vsync: this,
    );
    if (widget.value == null)
      _controller.repeat();
  }

  @override
  void didUpdateWidget(CustomLoader oldWidget) {
    super.didUpdateWidget(oldWidget);
    if (widget.value == null && !_controller.isAnimating)
      _controller.repeat();
    else if (widget.value != null && _controller.isAnimating)
      _controller.stop();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  final Animatable<double> _kStrokeHeadTween = CurveTween(
    curve: const Interval(0.0, 0.5, curve: Curves.fastOutSlowIn),
  ).chain(CurveTween(
    curve: const SawTooth(5),
  ));

  final Animatable<double> _kStrokeTailTween = CurveTween(
    curve: const Interval(0.5, 1.0, curve: Curves.fastOutSlowIn),
  ).chain(CurveTween(
    curve: const SawTooth(5),
  ));

  final Animatable<int> _kStepTween = StepTween(begin: 0, end: 5);

  final Animatable<double> _kRotationTween = CurveTween(curve: const SawTooth(5));

  Widget _buildIndicator(BuildContext context, double headValue, double tailValue, int stepValue, double rotationValue) {
    return widget._buildSemanticsWrapper(
      context: context,
      child: Container(
        constraints: const BoxConstraints(
          minWidth: _kMinCircularProgressIndicatorSize,
          minHeight: _kMinCircularProgressIndicatorSize,
        ),
        child: CustomPaint(
          painter: CustomLoaderPainter(
            valueColor: Colors.amber,
            value: widget.value, // may be null
            headValue: headValue, // remaining arguments are ignored if widget.value is not null
            tailValue: tailValue,
            stepValue: stepValue,
            rotationValue: rotationValue,
            strokeWidth: 2.0,
          ),
        ),
      ),
    );
  }

  Widget _buildAnimation() {
    return AnimatedBuilder(
      animation: _controller,
      builder: (BuildContext context, Widget child) {
        return _buildIndicator(
          context,
          _kStrokeHeadTween.evaluate(_controller),
          _kStrokeTailTween.evaluate(_controller),
          _kStepTween.evaluate(_controller),
          _kRotationTween.evaluate(_controller),
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    if (widget.value != null)
      return _buildIndicator(context, 0.0, 0.0, 0, 0.0);
    return _buildAnimation();
  }
}

